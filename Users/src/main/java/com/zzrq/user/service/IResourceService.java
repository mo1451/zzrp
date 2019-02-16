package com.zzrq.user.service;

import com.zzrq.user.dto.SysResource;

import java.util.List;

public interface IResourceService {
    List<SysResource> query(SysResource resource, int pageNum, int pageSize);

    Boolean add(List<SysResource> resources);

    Boolean change(List<SysResource> resources);

    Boolean delete(List<SysResource> resources);
}
