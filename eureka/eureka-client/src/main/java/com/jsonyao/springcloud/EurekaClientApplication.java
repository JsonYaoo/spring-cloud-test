package com.jsonyao.springcloud;

import com.netflix.discovery.DiscoveryManager;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Eureka Client测试启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(61));
        DiscoveryManager.getInstance().shutdownComponent();
    }
}
