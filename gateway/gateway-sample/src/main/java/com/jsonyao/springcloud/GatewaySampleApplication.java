package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Gateway样本应用
 */
@SpringBootApplication
@EnableDiscoveryClient
// 默认扫描本包中的FeignClients
@EnableFeignClients
public class GatewaySampleApplication {

    public static void main(String[] args) {
        // 使用webflux启动
        new SpringApplicationBuilder(GatewaySampleApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
