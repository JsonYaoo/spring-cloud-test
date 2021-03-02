package com.jsonyao.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

/**
 * Gateway配置测试类: 测试自定义路由规则
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    @Order
    public RouteLocator customizedRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route(p ->
                        // 配置路径断言
                        p.path("/java/**")
                        // 配置请求方法断言
                        .and().method(HttpMethod.POST)
                        // 配置header断言
                        .and().header("name")
                        .filters(f ->
                                // 配置截去再拼接过滤器
                                f.stripPrefix(1)
                                // 配置响应header过滤器
                                .addResponseHeader("java-param", "gateway-config")
                        )
                        // 配置转发uri
                        .uri("lb://FEIGN-CLIENT")
                ).build();
    }
}
