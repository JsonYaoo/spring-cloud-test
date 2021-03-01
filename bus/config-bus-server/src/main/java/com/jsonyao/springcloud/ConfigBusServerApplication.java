package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 总线式配置中心: Bus+Rabbitmq实现总线式改造
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigBusServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    //    获取配置文件的不同URL姿势，都是GET请求, 如果不指定{label}的话默认用master
    // 1. http://localhost:60000/{label}/{application}-{profile}.* (yml, properties, json)
    // 2. http://localhost:60000/{application}/{profile}/{label}
}