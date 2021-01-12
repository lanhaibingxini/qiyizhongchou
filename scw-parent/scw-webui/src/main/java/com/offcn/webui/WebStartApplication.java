package com.offcn.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //不开启jdbc
@EnableDiscoveryClient //让注册中心（并不局限于eureka注册中心）能够扫描到服务
@EnableFeignClients //开启feign远程调用功能
@EnableCircuitBreaker //开启hystrix熔断器
public class WebStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class);
    }
}
