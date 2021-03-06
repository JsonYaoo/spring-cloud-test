package com.jsonyao.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;

import java.time.ZonedDateTime;

/**
 * Gateway配置测试类: 测试自定义路由规则
 */
@Configuration
// @ConditionalOnBean没用, 注入的AuthFilter还是为null => 解决方式: GatewayConfiguration必须与自定义的Filter在同级目录注入时才不会为null
//@ConditionalOnBean({TimerFilter.class, AuthFilter.class})
public class GatewayConfiguration {

    // 利用Gateway实现Zuul After Filter
    @Autowired
    private TimerFilter timerFilter;

    /**
     * 测试网关JWT鉴权过滤器
     */
    @Autowired
    private AuthFilter authFilter;

    @Bean
    @Order
    public RouteLocator customizedRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                // 第一个路由规则
                .route(r ->
                        // 配置路径断言
                        r.path("/java/**")
                        // 配置请求方法断言
                        .and().method(HttpMethod.POST)
                        // 配置header断言
                        .and().header("name")
                        // 配置过滤器
                        .filters(f ->
                                // 配置截去再拼接过滤器
                                f.stripPrefix(1)
                                // 配置响应header过滤器
                                .addResponseHeader("java-param", "gateway-config")
                                // 利用Gateway实现Zuul After Filter
                                .filter(timerFilter)
                                // 测试网关JWT鉴权
                                .filter(authFilter)
                        )
                        // 配置转发uri
                        .uri("lb://FEIGN-CLIENT")
                )
                // 第二个路由规则
                .route(r ->
                        // 配置路径断言
                        r.path("/seckill/**")
                                // 配置After断言: 服务器启动后的2分钟生效
                                .and().after(ZonedDateTime.now().plusMinutes(2))
                                // 配置过滤器
                                .filters(f ->
                                        // 配置截去再拼接过滤器
                                        f.stripPrefix(1)
                                )
                                // 配置转发uri
                                .uri("lb://FEIGN-CLIENT")
                )
                .build();
    }
}
