package com.zzrq.config;


import com.zzrq.base.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RedisConfig extends BaseRedisConfig {

}
