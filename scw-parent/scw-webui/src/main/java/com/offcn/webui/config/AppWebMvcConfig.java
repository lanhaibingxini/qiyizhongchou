package com.offcn.webui.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web前端的配置类
 */
@Configuration
public class AppWebMvcConfig implements WebMvcConfigurer {

    /**
     * 跳转到login.html的配置方法
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //如果controller仅仅用于转发页面，那在当前方法中配置映射即可
        //添加视图控制器，设置视图名称
        registry.addViewController("login.html").setViewName("login");
    }
}
