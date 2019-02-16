package com.zzrq.controllers;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.dto.UserInfo;
import com.zzrq.dto.JWTProperties;
import com.zzrq.service.AuthService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController(value = "/auth")
@EnableConfigurationProperties(JWTProperties.class)
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 登录授权
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "校验登录并设置cookie", notes = "校验登录并设置cookie")
    @ApiImplicitParams({@ApiImplicitParam(name = "userInfo", value = "用户信息dto", required = true, dataType = "UserInfo")})
    @PostMapping("/authentication")
    public ResponseData authentication(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.authService.authentication(userInfo,request,response);
    }

    /**
     * 登录授权
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "校验cookie", notes = "校验cookie")
    @PostMapping("/verify")
    public ResponseData verify(@CookieValue("zzrq_token")String token, HttpServletRequest request, HttpServletResponse response) {
        return this.authService.verify(token,request,response);
    }

}
