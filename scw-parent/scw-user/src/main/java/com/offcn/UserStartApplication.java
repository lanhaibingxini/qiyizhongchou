package com.offcn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //开启eureka注册中心客户端
@MapperScan("com.offcn.user.mapper") //扫描mapper映射文件
public class UserStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserStartApplication.class);
    }
}
