package com.jsonyao.springcloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Dubbo测试应用: 服务消费者
 */
@SpringBootApplication
@EnableDubbo
public class DubboConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboConsumerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
