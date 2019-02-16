package com.zzrq.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.utils.NumberUtil;
import com.zzrq.base.utils.RSAUtil;
import com.zzrq.user.dto.SysUser;
import com.zzrq.user.mapper.UserMapper;
import com.zzrq.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${user.publicKey}")
    private String publicKey;

    @Value("${user.privateKey}")
    private String privateKey;

    private static final String KEY_PREFIX = "user:code:phone:";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Boolean checkData(String data, Integer type) {
        SysUser sysUser = new SysUser();
        switch (type) {
            case 1:
                sysUser.setName(data);
                break;
            case 2:
                sysUser.setEmail(data);
                break;
            case 3:
                sysUser.setPhone(data);
                break;
            default:
                sysUser.setName(data);
        }
        int count = userMapper.selectCount(sysUser);
        return count != 0;
    }

    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        String code = NumberUtil.generateCode(6);
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<>();
            msg.put("phone", phone);
            msg.put("code", code);
            this.amqpTemplate.convertAndSend("zzrq.message.exchange", "message.verify.code", msg);
            // 将code存入redis
            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    @Override
    public Boolean register(SysUser sysUser, String code) {
        String key = KEY_PREFIX + sysUser.getPhone();
        // 从redis取出验证码
        String codeCache = this.redisTemplate.opsForValue().get(key);
        // 检查验证码是否正确
        if (!code.equals(codeCache)) {
            // 不正确，返回
            return false;
        }

        boolean boo = this.add(sysUser);

        // 如果注册成功，删除redis中的code
        if (boo) {
            try {
                this.redisTemplate.delete(key);
            } catch (Exception e) {
                logger.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
        return boo;
    }

    @Override
    public Boolean add(List<SysUser> sysUsers) {
        for (SysUser sysUser : sysUsers) {
            this.add(sysUser);
        }
        return true;
    }

    private Boolean add(SysUser sysUser) {
        sysUser.setId(null);
        sysUser.setCreateDate(new Date());
        sysUser.setLastUpdateDate(new Date());
        if(StringUtils.isEmpty(sysUser.getPassword())) {
            sysUser.setPassword("123456");
        }
        try {
            sysUser.setPassword(RSAUtil.encrypt(sysUser.getPassword(), publicKey));
        } catch (Exception e) {
            logger.error("密码加密失败，password：{}", sysUser.getPassword(), e);
            return false;
        }
        // 写入数据库
        this.userMapper.insertSelective(sysUser);
        return true;
    }

    @Override
    public Boolean change(List<SysUser> sysUsers) {
        for (SysUser sysUser : sysUsers) {
            if(!StringUtils.isEmpty(sysUser.getPassword())) {
                try {
                    sysUser.setPassword(RSAUtil.encrypt(sysUser.getPassword(), publicKey));
                } catch (Exception e) {
                    logger.error("密码加密失败，password：{}", sysUser.getPassword(), e);
                    return false;
                }
            }
            sysUser.setLastUpdateDate(new Date());
            userMapper.updateByPrimaryKeySelective(sysUser);
        }

        return true;
    }

    @Override
    public Boolean delete(List<SysUser> sysUsers) {
        for (SysUser sysUser : sysUsers) {
            userMapper.deleteByPrimaryKey(sysUser.getId());
        }
        return true;
    }

    @Override
    public List<SysUser> query(SysUser sysUser, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers = userMapper.select(sysUser);
        return sysUsers;
    }

    @Override
    public ResponseData checkPassword(SysUser sysUser) {
        String msg = "";
        ResponseData responseData = new ResponseData();
        if (StringUtils.isEmpty(sysUser.getName()) && StringUtils.isEmpty(sysUser.getPhone())) {
            logger.error("用户名或手机号为空");
            msg += "用户名或手机号为空";
        } else {

            SysUser querySysUser = new SysUser();
            querySysUser.setName(sysUser.getName());
            querySysUser.setPhone(sysUser.getPhone());
            querySysUser = this.userMapper.selectOne(querySysUser);

            if(querySysUser != null) {
                if (StringUtils.isEmpty(sysUser.getPassword())) {
                    logger.error("密码为空，name：{}", sysUser.getName());
                    msg += "密码为空";
                } else {
                    if (!StringUtils.isEmpty(querySysUser.getPassword())) {
                        try {
                            if (!sysUser.getPassword().equals(RSAUtil.decrypt(querySysUser.getPassword(), privateKey))) {
                                msg += "账号或密码错误";
                            } else {
                                List<SysUser> sysUsers = new ArrayList<>();
                                sysUsers.add(querySysUser);
                                responseData.setRows(sysUsers);
                            }
                        } catch (Exception e) {
                            logger.error("密码解密失败，name：{}", querySysUser.getName(), e);
                            msg += "密码解密失败，name：" + querySysUser.getName();
                            return new ResponseData(msg);
                        }
                    }
                }

            } else {
                msg += "账号或密码错误";
            }
        }
        if(StringUtils.isEmpty(msg)) {
            return responseData;
        } else {
            return new ResponseData(msg);
        }
    }

    @Override
    public String changPassword(SysUser sysUser) {
        ResponseData responseData = this.checkPassword(sysUser);
        String msg = "";
        if (responseData.isSuccess()) {
            SysUser querySysUser = this.userMapper.selectOne(sysUser);
            try {
                sysUser.setPassword(RSAUtil.encrypt(sysUser.getNewPassword(), publicKey));
            } catch (Exception e) {
                logger.error("密码加密失败，password：{}", sysUser.getPassword(), e);
                msg += "密码加密失败";
            }
            sysUser.setLastUpdateDate(new Date());
            userMapper.updateByPrimaryKeySelective(querySysUser);
        } else {
            responseData.setMessage(msg);
        }
        return msg;
    }

    @Override
    public List<SysUser> queryUser(String data, int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        Example example = new Example(SysUser.class);
        if(!StringUtils.isEmpty(data)) {
            data = "%" + data + "%";
            example.createCriteria().orLike("name", data)
                    .orLike("phone", data);
        }
        return this.userMapper.selectByExample(example);
    }

    @Override
    public String checkVerifyCode(String phone, String code) {
        String msg = "";
        String key = KEY_PREFIX + phone;
        // 从redis取出验证码
        String codeCache = this.redisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(codeCache)) {
            msg += "验证码未生成或者已过期";
        } else {
            // 检查验证码是否正确
            if (!code.equals(codeCache)) {
                // 不正确，返回
                msg += "验证码错误";
            }
        }

        return msg;
    }
}
