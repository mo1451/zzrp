package com.zzrq.user.service;

import com.zzrq.user.dto.SysRole;
import com.zzrq.user.dto.SysUser;
import com.zzrq.user.dto.SysUserRole;

import java.util.List;

public interface IUserRoleService {
    List<SysRole> queryRoles(Long userId, int pageNum, int pageSize);

    List<SysUser> queryUsers(Long roleId, int pageNum, int pageSize);

    Boolean add(List<SysUserRole> sysUserRoles);

    Boolean delete(List<SysUserRole> sysUserRole);
}
