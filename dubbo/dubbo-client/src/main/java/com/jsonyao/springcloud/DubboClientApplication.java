package com.jsonyao.springcloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Dubbo测试应用: 服务提供者
 */
@SpringBootApplication
@EnableDubbo
public class DubboClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
