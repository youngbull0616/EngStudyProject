package com.zhangxin.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        System.out.println("cors策略已启动！！！");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:8080"); // 允许的域名
        config.addAllowedHeader("*"); // 允许的请求头
        config.addAllowedMethod("*"); // 允许的请求方法
        source.registerCorsConfiguration("/**", config); // 对所有路径应用配置
        return new CorsFilter(source);
    }
}
