package com.zzrq.base.dto;



import java.util.Date;

public class BaseDto {
    private Date lastUpdateDate;
    private Date createDate;
    private Long lastUpdateBy;
    private Long createBy;
    private Long version;

    @Override
    public String toString() {
        return "BaseDto{" +
                "lastUpdateDate=" + lastUpdateDate +
                ", createDate=" + createDate +
                ", lastUpdateBy=" + lastUpdateBy +
                ", createBy=" + createBy +
                '}';
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(Long lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
}
