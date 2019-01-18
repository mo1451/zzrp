package com.zzrq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.zzrq.batch.mapper")
public class Batch {
    public static void main(String[] args) {
        SpringApplication.run(Batch.class);
    }
}
