package com.zzrq.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.dto.SysUser;
import com.zzrq.user.dto.SysUserRole;
import com.zzrq.user.mapper.RoleMapper;
import com.zzrq.user.mapper.UserMapper;
import com.zzrq.user.mapper.UserRoleMapper;
import com.zzrq.user.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<SysRole> queryRoles(Long userId, int pageNum, int pageSize) {
        Example roleExample = new Example(SysRole.class);
        String condition = "exists (select 1 from sys_user_role sur where id = sur.role_id and sur.user_id = " + userId + " )";
        roleExample.createCriteria().andCondition(condition)
        .andEqualTo("enableFlag","Y");
        PageHelper.startPage(pageNum,pageSize);
        return roleMapper.selectByExample(roleExample);
    }

    @Override
    public List<SysUser> queryUsers(Long roleId, int pageNum, int pageSize) {
        Example roleExample = new Example(SysUser.class);
        String condition = "exists (select 1 from sys_user_role sur where id = sur.user_id and sur.role_id = " + roleId + " )";
        roleExample.createCriteria().andCondition(condition);
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.selectByExample(roleExample);
    }

    @Override
    public Boolean add(List<SysUserRole> sysUserRoles) {
        for (SysUserRole sysUserRole : sysUserRoles) {
            sysUserRole.setLastUpdateDate(new Date());
            sysUserRole.setCreateDate(new Date());
            this.userRoleMapper.insertSelective(sysUserRole);
        }
        return true;
    }

    @Override
    public Boolean delete(List<SysUserRole> sysUserRoles) {
        for (SysUserRole sysUserRole : sysUserRoles) {
            this.userRoleMapper.deleteByPrimaryKey(sysUserRole);
        }
        return true;
    }
}
