package com.jsonyao.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon Consumer测试: RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 配置@LoadBalanced注解
     * @return
     */
    @Bean("restTemplate")
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
