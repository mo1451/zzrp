package com.zzrq.user.dto;

import com.zzrq.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "sys_user_role")
@ApiModel(value = "用户角色关系")
public class SysUserRole extends BaseDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
    private Long id;
    @NotNull
    private Long userId;
    @NotNull
    private Long roleId;

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
