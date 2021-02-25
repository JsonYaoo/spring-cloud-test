package com.jsonyao.springcloud;

import com.jsonyao.springcloud.service.IHystrixFallbackService;
import feign.Feign;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 测试HystrixFallback降级应用
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class HystrixFallbackApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(HystrixFallbackApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

//        // 借助Feign.configKey()生成Feign使用的方法签名
//        try {
//            Class clazz = IHystrixFallbackService.class;
//            System.out.println(Feign.configKey(clazz, clazz.getMethod("timeout", int.class)));
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }
}
