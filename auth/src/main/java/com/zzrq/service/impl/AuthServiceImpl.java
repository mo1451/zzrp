package com.zzrq.service.impl;


import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.dto.UserInfo;
import com.zzrq.base.utils.CookieUtil;
import com.zzrq.base.utils.JWTUtil;
import com.zzrq.dto.JWTProperties;
import com.zzrq.service.AuthService;
import com.zzrq.service.UserFeign;
import io.jsonwebtoken.Claims;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserFeign userFeign;

    @Autowired
    private JWTProperties properties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:token:";

    @Override
    public ResponseData authentication(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
        String token = "";
        ResponseData responseData = userFeign.checkUserPassword(userInfo);
        if(!responseData.isSuccess()) {
            if(StringUtils.isNotEmpty(userInfo.getPhone())) {
                responseData = userFeign.checkVerifyCode(userInfo.getPhone(), userInfo.getPassword());
            }
        }
        if(responseData.isSuccess()) {
            UserInfo info = new UserInfo();
            try {
                BeanUtils.copyProperties(info,responseData.getRows().get(0));
                token = JWTUtil.createJWT(properties.getExpire(),info, properties.getPrivateKey());
             /*   List<String> tokens = new ArrayList<>();
                tokens.add(token);
                responseData.setRows(tokens);*/
                // 将code存入redis
                redisTemplate.opsForValue().set(KEY_PREFIX + info.getId(), token, properties.getExpire(), TimeUnit.MINUTES);
                CookieUtil.setCookie(properties.getCookieName(), (String) responseData.getRows().get(0), properties.getCookieMaxAge(), request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseData;
    }

    @Override
    public ResponseData verify(String token, HttpServletRequest request, HttpServletResponse response) {
        ResponseData responseData = new ResponseData();
        try {
            Claims claims = JWTUtil.parseJWT(token, properties.getPublicKey());
            UserInfo userInfo = new UserInfo();
            BeanUtils.populate(userInfo,claims);
            Long id = userInfo.getId();
            if(id != null) {
                String redisToken = this.redisTemplate.opsForValue().get(id);
                if(StringUtils.isNotEmpty(redisToken) && redisToken.equals(token)) {
                    token = JWTUtil.createJWT(properties.getExpire(),userInfo, properties.getPrivateKey());
                    redisTemplate.opsForValue().set(KEY_PREFIX + userInfo.getId(), token, properties.getExpire(), TimeUnit.MINUTES);
                    CookieUtil.setCookie(properties.getCookieName(), token, properties.getCookieMaxAge(), request, response);
                    responseData.setOneRow(userInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseData.setMessage(e.getMessage());
        }
        return responseData;
    }
}
