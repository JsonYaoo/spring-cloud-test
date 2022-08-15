package com.jsonyao.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * Feign Consumer应用: 基于公共服务接口的下游服务消费者(与接口相同的包路径)
 */
@SpringBootApplication
// 由于是服务提供者, 需要Eureka注册中心拉取服务
@EnableDiscoveryClient
// 同时还需要EnableFeignClient, 因为要发起服务调用, 使得Feign接口能动态代理出实现类
@EnableFeignClients
public class FeignClientIntfConsumerApplication1 implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignClientIntfConsumerApplication1.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Environment environment = applicationContext.getEnvironment();
        System.err.println(environment.getProperty("ribbon.ReadTimeout", "1000"));
        System.err.println(environment.getProperty("feign-client.ribbon.ReadTimeout", "1000"));
    }
}
