package com.zzrq.user.service;

import com.zzrq.base.dto.ResponseData;
import com.zzrq.user.dto.SysUser;

import java.util.List;

public interface IUserService {

    Boolean checkData(String data, Integer type);

    Boolean sendVerifyCode(String phone);

    Boolean register(SysUser sysUser, String code);

    ResponseData checkPassword(SysUser sysUser);

    List<SysUser> queryUser(String data, int page, int pageSize);

    String changPassword(SysUser sysUser);

    String checkVerifyCode(String phone, String code);

    Boolean add(List<SysUser> sysUsers);

    Boolean change(List<SysUser> sysUsers);

    Boolean delete(List<SysUser> sysUsers);

    List<SysUser> query(SysUser sysUser, int pageNum, int pageSize);
}
