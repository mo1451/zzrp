package com.zzrq.user.dto;

import com.zzrq.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "sys_role_resource")
@ApiModel(value = "资源dto")
public class SysRoleResource extends BaseDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
    private Long id;
    @NotNull
    private Long roleId;
    @NotNull
    private Long resourceId;

    @Override
    public String toString() {
        return "SysRoleResource{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", resourceId=" + resourceId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
