package com.zzrq.dto;

import com.zzrq.base.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "zzrq.jwt")
public class JWTProperties {

    private String cookieName;// cookie名称

    private static final Logger logger = LoggerFactory.getLogger(JWTProperties.class);


    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }
}
