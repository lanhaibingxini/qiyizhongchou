package com.offcn.webui.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign远程控制的配置类
 */
@Configuration
public class FeignConfig {

    /**
     * 获取feign日志的方法
     * @return
     */
    @Bean
    public Logger.Level getFeignlogger(){
        return Logger.Level.FULL;
    }
}
