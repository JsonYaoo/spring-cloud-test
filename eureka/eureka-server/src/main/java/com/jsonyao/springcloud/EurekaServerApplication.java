package com.jsonyao.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server测试启动类
 */
@SpringBootApplication
//@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
//        new SpringApplicationBuilder(EurekaServerApplication.class)
//                .web(WebApplicationType.SERVLET)
//                .run(args);
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
