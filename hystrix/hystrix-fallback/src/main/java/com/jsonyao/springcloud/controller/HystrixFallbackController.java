package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.IHystrixFallbackService;
import com.jsonyao.springcloud.service.IRequestCacheService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试HystrixFallback降级应用: 前端控制器
 */
@RestController
public class HystrixFallbackController {

    @Autowired
    private IHystrixFallbackService iHystrixFallbackService;

    @Autowired
    private IRequestCacheService iRequestCacheService;

    /**
     * Hystrix直接异常降级测试
     * @return
     */
    @GetMapping("/fallback")
    public String fallback() {
        return iHystrixFallbackService.error();
    }

    /**
     * Hystrix超时降级测试1: 由于Hystrix超时中断线程的异常被捕获住但又不做处理, 看不到效果, 所以重新做一个测试2
     * @return
     */
    @GetMapping("/timeout")
    public String timeout(Integer timeout) {
        return iHystrixFallbackService.retry(timeout);
    }

    /**
     * Hystrix超时降级测试2: 由于Hystrix超时中断线程的异常被捕获住但又不做处理, 看不到效果, 所以重新做一个测试2
     * => 测试结果: 失败, 打断线程并没奏效, Feign接口超时Hystrix降级时, 并不会打断原来线程
     * @return
     */
    @PostMapping("/timeout2")
    public String timeout2(Integer timeout) {
        // 使用Lombok关闭:  打开Hystrix上下文 => 测试结果: 还是失败, 打断线程并没奏效, Feign接口超时Hystrix降级时, 并不会打断原来线程
//        @Cleanup HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        return iHystrixFallbackService.timeout(timeout);
    }

    /**
     * Hystrix RequestCache测试: 同一个Hystrix上下文同样的CacheKey只会调用一次服务
     * @return
     */
    @PostMapping("/cache")
    public Friend cache(String name) {
        // 2. 使用Lombok关闭:  打开Hystrix上下文
        @Cleanup HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        // 1. 原生关闭: 打开Hystrix上下文
//        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();

        // 模拟同一个key请求两次
        Friend friend;
        try {
            friend = iRequestCacheService.requestCache(name);

            // 测试不同Key会不会触发缓存 => 不会触发, 只有相同的key才会, 而且连降级的结果也会缓存
//            name = "fallback";

            friend = iRequestCacheService.requestCache(name);
        } finally {
            // 1. 原生关闭
//            hystrixRequestContext.close();
        }

        return friend;
    }
}
