package com.jsonyao.cloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Feign Client应用: 基于公共服务接口的上游服务提供者
 */
@SpringBootApplication
// 由于是服务提供者, 需要Eureka注册中心注册服务, 但是不需要EnableFeignClient, 因为不用发起服务调用
@EnableDiscoveryClient
public class FeignClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
