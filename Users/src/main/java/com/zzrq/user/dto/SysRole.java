package com.zzrq.user.dto;

import com.zzrq.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "sys_role")
@ApiModel(value = "角色dto")
public class SysRole extends BaseDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
    private Long id;
    @NotNull
    private String role;
    @NotNull
    private String name;
    private String enableFlag;

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", name='" + name + '\'' +
                ", enableFlag='" + enableFlag + '\'' +
                '}';
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
