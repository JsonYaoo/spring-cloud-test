package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 测试网关JWT鉴权: 服务提供应用
 */
@SpringBootApplication
// 一般用@EnableDiscoveryClient可以与底层注册中心组件解耦, 避免使用@EnableEurekaClient
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
