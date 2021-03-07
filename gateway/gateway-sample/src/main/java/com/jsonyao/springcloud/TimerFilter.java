package com.jsonyao.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 利用Gateway实现Zuul After Filter
 */
@Component
@Slf4j
// 路由过滤器
public class TimerFilter implements GatewayFilter, Ordered {
// 全局Global过滤器 GlobalFilter
//public class TimerFilter implements GlobalFilter, Ordered {

    /**
     * 执行过滤逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch stopWatch = new StopWatch();

        // 将打印访问路径
        stopWatch.start(exchange.getRequest().getURI().getRawPath());
        exchange.getAttributes().put("requestTimeBegain", System.currentTimeMillis());

        // 后置过滤器(钩子实现)
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    // 钩子实现空参run方法
                    stopWatch.stop();
                    log.info(stopWatch.prettyPrint());
                })
        );
    }

    /**
     * 高优先级: 小, 低优先级: 大 => 后置过滤器则反过来(钩子)
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
