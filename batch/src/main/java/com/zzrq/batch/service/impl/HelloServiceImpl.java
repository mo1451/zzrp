package com.zzrq.batch.service.impl;

import com.zzrq.batch.job.HelloJob;
import com.zzrq.batch.service.IHelloService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello() {
        System.out.println(new Date() + "----------Hello");
        return "hello";
    }
}
