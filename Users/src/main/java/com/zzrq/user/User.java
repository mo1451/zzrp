package com.zzrq.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan("com.zzrq.user.mapper")
public class User {
    public static void main(String[] args) {
        SpringApplication.run(User.class);
    }
}
