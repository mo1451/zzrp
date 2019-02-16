package com.zzrq.service;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FeignClient(value = "auth-service")
@RestController(value = "/auth")
@Component
public interface AuthFeign {
    @PostMapping(value = "/verify")
    ResponseData verify(@CookieValue("zzrq_token")String token, HttpServletRequest request, HttpServletResponse response);
}
