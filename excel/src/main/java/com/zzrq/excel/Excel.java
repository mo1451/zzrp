package com.zzrq.excel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Excel {
    public static void main(String[] args) {
        SpringApplication.run(Excel.class);
    }
}
