package com.zzrq.service;


import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service")
@RequestMapping("/user")
@Component
public interface UserFeign {
    @PostMapping(value = "/check/password", consumes = "application/json")
    ResponseData checkUserPassword(@RequestBody UserInfo userInfo);

    @GetMapping("/check/code/{phone}/{code}")
    ResponseData checkVerifyCode(@PathVariable("phone") String phone, @PathVariable(value = "code") String code);
}
