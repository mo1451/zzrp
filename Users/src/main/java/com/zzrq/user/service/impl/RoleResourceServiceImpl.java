package com.zzrq.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.zzrq.user.dto.SysResource;
import com.zzrq.user.dto.SysRole;
import com.zzrq.user.dto.SysRoleResource;
import com.zzrq.user.dto.SysUserRole;
import com.zzrq.user.mapper.ResourceMapper;
import com.zzrq.user.mapper.RoleMapper;
import com.zzrq.user.mapper.RoleResourceMapper;
import com.zzrq.user.service.IRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class RoleResourceServiceImpl implements IRoleResourceService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;


    @Override
    public List<SysResource> queryResources(Long roleId, int pageNum, int pageSize) {
        Example example = new Example(SysResource.class);
        String condition = "exists (select 1 from sys_role_resource srr where id = srr.resource_id and srr.role_id = " + roleId + " )";
        example.createCriteria().andCondition(condition)
                .andEqualTo("enableFlag","Y");
        PageHelper.startPage(pageNum,pageSize);
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<SysRole> queryRoles(Long resourceId, int pageNum, int pageSize) {
        Example example = new Example(SysRole.class);
        String condition = "exists (select 1 from sys_role_resource srr where id = srr.role_id and srr.resource_id = " + resourceId + " )";
        example.createCriteria().andCondition(condition)
                .andEqualTo("enableFlag","Y");
        PageHelper.startPage(pageNum,pageSize);
        return roleMapper.selectByExample(example);
    }

    @Override
    public Boolean add(List<SysRoleResource> sysRoleResources) {
        for (SysRoleResource sysRoleResource : sysRoleResources) {
            sysRoleResource.setLastUpdateDate(new Date());
            sysRoleResource.setCreateDate(new Date());
            this.roleResourceMapper.insertSelective(sysRoleResource);
        }
        return true;
    }

    @Override
    public Boolean delete(List<SysRoleResource> sysRoleResources) {
        for (SysRoleResource sysRoleResource : sysRoleResources) {
            this.roleResourceMapper.deleteByPrimaryKey(sysRoleResource);
        }
        return true;
    }

    @Override
    public Boolean change(List<SysRoleResource> sysRoleResources) {
        for (SysRoleResource sysRoleResource : sysRoleResources) {
            if(sysRoleResource.getId() != null) {
                this.roleResourceMapper.deleteByPrimaryKey(sysRoleResource);
            } else {
                sysRoleResource.setLastUpdateDate(new Date());
                sysRoleResource.setCreateDate(new Date());
                this.roleResourceMapper.insertSelective(sysRoleResource);
            }
        }
        return true;
    }

    @Override
    public List<SysResource> queryAllResources(Long roleId, int pageNum, int pageSize) {
        Example example = new Example(SysResource.class);
        String condition = "exists (select 1 from sys_role_resource srr where id = srr.resource_id and srr.role_id = " + roleId + " )";
        example.createCriteria().andCondition(condition)
                .andEqualTo("enableFlag","Y");
        List<SysResource> sysResources = resourceMapper.selectByExample(example);

        Example resourceExample = new Example(SysResource.class);
        example.createCriteria().andCondition(condition)
                .andEqualTo("enableFlag","Y");
        List<SysResource> allSysResources = resourceMapper.selectByExample(resourceExample);

        for (SysResource allSysResource : allSysResources) {
            allSysResource.setEnableFlag("N");
            for (SysResource sysResource : sysResources) {
                if(sysResource.getId().equals(allSysResource.getId())) {
                    allSysResource.setEnableFlag("Y");
                }
            }
        }
        return allSysResources;
    }
}
