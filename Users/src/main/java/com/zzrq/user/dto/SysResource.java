package com.zzrq.user.dto;

import com.zzrq.base.dto.BaseDto;
import io.swagger.annotations.ApiModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "sys_resource")
@ApiModel(value = "资源dto")
public class SysResource extends BaseDto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY,generator = "Mysql")
    private Long id;

    @NotNull
    private String url;
    private String name;
    private String enableFlag;

    @Override
    public String toString() {
        return "SysResource{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", enableFlag='" + enableFlag + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }
}
