package com.jsonyao.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultFeignConfiguration {
    @Bean
    public feign.Logger.Level feginLoggerLevel() {
        // 记录请求和响应的标头、正文和元数据
        return feign.Logger.Level.FULL;
    }
}
