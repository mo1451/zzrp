package com.zzrq.user.service;

import com.zzrq.user.dto.User;

import java.util.List;

public interface IUserService {

    Boolean checkData(String data, Integer type);

    Boolean sendVerifyCode(String phone);

    Boolean register(User user, String code);

    String checkPassword(User user);

    List<User> queryUser(String data, int page, int pageSize);

    String changPassword(User user);

    String checkVerifyCode(String phone, String code);
}
