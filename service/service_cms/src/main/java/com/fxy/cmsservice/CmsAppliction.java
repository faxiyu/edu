package com.fxy.cmsservice;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fxy.cmsservice.mapper")
@ComponentScan(basePackages = {"com.fxy"})
@EnableDiscoveryClient //nacos注册
public class CmsAppliction {
    public static void main(String[] args) {
        SpringApplication.run(CmsAppliction.class);
    }
}
