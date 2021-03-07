package com.jsonyao.springcloud;

import com.jsonyao.springcloud.AuthResponse;
import com.jsonyao.springcloud.AuthResponseCode;
import com.jsonyao.springcloud.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 测试网关JWT鉴权: 网关鉴权核心逻辑
 */
@Component
@Slf4j
// 全局Global过滤器 GlobalFilter
public class AuthFilter implements GatewayFilter, Ordered {

    /**
     * Token Header键
     */
    private static final String AUTH = "Authorization";

    /**
     * UserName Header键
     */
    private static final String USERNAME = "imooc-user-name";

    /**
     * 注入FeignClient鉴权服务
     */
    @Autowired
    private AuthService authService;

    /**
     * 执行过滤逻辑
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Auth start");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTH);
        String username = headers.getFirst(USERNAME);

        if(StringUtils.isBlank(token)){
            log.error("token not found");
            // 401: 未登录
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 提前完成response响应
            return response.setComplete();
        }

        // 使用Header中的Token与UserName来鉴权
        AuthResponse authResponse = authService.verify(token, username);
        if(authResponse.getCode() != AuthResponseCode.SUCCESS){
            log.error("invalid token");
            // 403: 非法登录
            response.setStatusCode(HttpStatus.FORBIDDEN);
            // 提前完成response响应
            return response.setComplete();
        }

        // 将用户信息存放在请求header中传递给下游业务, 此处省略...
        ServerHttpRequest.Builder mutate = request.mutate();
        mutate.header("imooc-user-name", username);
        request = mutate.build();// 这里使用生成新的request方式, 主要是为了不让下游看到token

        // 如果响应中需要放数据，也可以放在response的header中, 此处省略...
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("imooc-user-name", username);

        // 执行过滤器执行链下一个目标
        return chain.filter(
                exchange.mutate()
                .request(request)
                .response(response)
                .build()
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
