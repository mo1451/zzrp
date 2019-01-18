package com.zzrq.base.dto;


import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ResponseData {
    private String code;

    private String message = "success";

    private List<?> rows;

    private boolean success = true;

    private Long total;

    public ResponseData() {
    }

    public ResponseData(String message) {
        if(!StringUtils.isEmpty(message)) {
            setMessage(message);
            setSuccess(false);
        } else {
            setSuccess(true);
            setMessage("success");
        }
    }

    public ResponseData(boolean success) {
        setSuccess(success);
    }

    public ResponseData(List<?> list) {
        this(true);
        setRows(list);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<?> getRows() {
        return rows;
    }

    public Long getTotal() {
        return total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
        setTotal((long) rows.size());
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
