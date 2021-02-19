package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server集群+互备测试应用1
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerPeer1Appliction {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerPeer1Appliction.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
