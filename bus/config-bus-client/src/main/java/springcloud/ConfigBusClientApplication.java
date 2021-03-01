package springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 总线式配置中心客户端: 可拉取配置中心的配置文件属性
 */
@SpringBootApplication
// 配置高可用的配置中心的客户端: 从Eureka那里拉取配置中心列表
@EnableDiscoveryClient
public class ConfigBusClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}