package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Gateway样本应用
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewaySampleApplication {

    public static void main(String[] args) {
        // 使用webflux启动
        new SpringApplicationBuilder(GatewaySampleApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
