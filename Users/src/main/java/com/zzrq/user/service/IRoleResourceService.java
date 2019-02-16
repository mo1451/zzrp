package com.zzrq.user.service;

import com.zzrq.user.dto.*;

import java.util.List;

public interface IRoleResourceService {
    

    Boolean add(List<SysRoleResource> sysRoleResources);

    Boolean delete(List<SysRoleResource> sysRoleResources);

    List<SysResource> queryResources(Long roleId, int pageNum, int pageSize);

    List<SysRole> queryRoles(Long resourceId, int pageNum, int pageSize);

    Boolean change(List<SysRoleResource> sysRoleResources);

    List<SysResource> queryAllResources(Long roleId, int pageNum, int pageSize);
}
