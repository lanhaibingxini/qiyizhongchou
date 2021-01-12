package com.offcn.project.config;

import com.offcn.util.OssTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS的配置类
 */
@Configuration
public class AppProjectConfig {
    @Bean
    @ConfigurationProperties(prefix = "oss") //加载配置文件中的oss属性
    public OssTemplate ossTemplate(){
        return new OssTemplate();
    }
}
