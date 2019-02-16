package com.zzrq.user.service.impl;

import com.zzrq.user.dto.SysResource;
import com.zzrq.user.mapper.ResourceMapper;
import com.zzrq.user.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<SysResource> query(SysResource resource, int pageNum, int pageSize) {
        return resourceMapper.select(resource);
    }

    @Override
    public Boolean add(List<SysResource> resources) {
        for (SysResource resource : resources) {
            resource.setCreateDate(new Date());
            resource.setLastUpdateDate(new Date());
            resourceMapper.insertSelective(resource);
        }
        return true;
    }

    @Override
    public Boolean change(List<SysResource> resources) {
        for (SysResource resource : resources) {
            resource.setLastUpdateDate(new Date());
            resourceMapper.updateByPrimaryKeySelective(resource);
        }
        return true;
    }

    @Override
    public Boolean delete(List<SysResource> resources) {
        for (SysResource resource : resources) {
            resourceMapper.deleteByPrimaryKey(resource);
        }
        return true;
    }
}
