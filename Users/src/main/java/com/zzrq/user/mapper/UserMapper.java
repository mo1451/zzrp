package com.zzrq.user.mapper;

import com.zzrq.user.dto.SysUser;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface UserMapper extends Mapper<SysUser> {

}
