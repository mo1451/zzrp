package com.zzrq.service;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.base.dto.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    ResponseData authentication(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response);

    ResponseData verify(String token, HttpServletRequest request, HttpServletResponse response);
}
