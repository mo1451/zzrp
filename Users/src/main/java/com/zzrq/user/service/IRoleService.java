package com.zzrq.user.service;

import com.zzrq.user.dto.SysRole;

import java.util.List;

public interface IRoleService {
    Boolean add(List<SysRole> sysRoles);

    Boolean change(List<SysRole> sysRoles);

    Boolean delete(List<SysRole> sysRoles);

    List<SysRole> query(SysRole sysRole, int pageNum, int pageSize);
}
