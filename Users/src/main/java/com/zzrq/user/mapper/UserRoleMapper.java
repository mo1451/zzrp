package com.zzrq.user.mapper;

import com.zzrq.user.dto.SysUserRole;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface UserRoleMapper extends Mapper<SysUserRole> {
}
