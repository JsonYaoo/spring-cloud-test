package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Ribbon Consumer测试启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class RibbonConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
