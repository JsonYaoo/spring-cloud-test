package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心高可用改造: 借助Eureka实现高可用
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerEurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerEurekaApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    //    获取配置文件的不同URL姿势，都是GET请求, 如果不指定{label}的话默认用master
    // 1. http://localhost:60000/{label}/{application}-{profile}.* (yml, properties, json)
    // 2. http://localhost:60000/{application}/{profile}/{label}
}