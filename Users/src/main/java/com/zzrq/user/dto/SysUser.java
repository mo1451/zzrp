package com.zzrq.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzrq.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Table(name = "sys_user")
@ApiModel(value = "用户dto")
public class SysUser extends BaseDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @Length(min = 4,max = 20,message = "用户名长度只能在4至20之前")
    @NotNull
    private String name;
    @NotNull
    private String code;

    @JsonIgnore
    private String password;

    @Email
    private String email;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private Date enableDate;

    private Date disableDate;

    @Transient
    private String newPassword;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", enableDate=" + enableDate +
                ", disableDate=" + disableDate +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getEnableDate() {
        return enableDate;
    }

    public void setEnableDate(Date enableDate) {
        this.enableDate = enableDate;
    }

    public Date getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(Date disableDate) {
        this.disableDate = disableDate;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
