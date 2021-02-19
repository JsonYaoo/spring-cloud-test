package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server集群+互备测试应用2
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerPeer2Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerPeer2Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
