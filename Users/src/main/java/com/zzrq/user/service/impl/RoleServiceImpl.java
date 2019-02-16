package com.zzrq.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.mapper.RoleMapper;
import com.zzrq.user.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Boolean add(List<SysRole> sysRoles) {
        for (SysRole sysRole : sysRoles) {
            sysRole.setCreateDate(new Date());
            sysRole.setLastUpdateDate(new Date());
            roleMapper.insertSelective(sysRole);
        }
        return true;
    }

    @Override
    public Boolean change(List<SysRole> sysRoles) {
        for (SysRole sysRole : sysRoles) {
            sysRole.setLastUpdateDate(new Date());
            roleMapper.updateByPrimaryKeySelective(sysRole);
        }
        return true;
    }

    @Override
    public Boolean delete(List<SysRole> sysRoles) {
        for (SysRole sysRole : sysRoles) {
            roleMapper.deleteByPrimaryKey(sysRole);
        }
        return true;
    }

    @Override
    public List<SysRole> query(SysRole sysRole, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return roleMapper.select(sysRole);
    }
}
