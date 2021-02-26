package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.IHystrixFallbackService;
import com.jsonyao.springcloud.service.IRequestCacheService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
     * Hystrix超时降级测试3: 这里降级的不是服务, 而是控制器本身的方法
     * => 测试结果: 降级成功, 但是如果控制器配置的与服务配置的超时时长一样大, 那么是触发控制器的降级方法; 否则, 触发配置时长小的降级方法
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "timeout3Fallback",
            commandProperties = {
                    // 3. Hystrix超时策略: 指定服务中某个方法的超时配置(优先级比全局的高)
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="3000")
            }
    )
    @PostMapping("/timeout3")
    public String timeout3(Integer timeout) {
        return iHystrixFallbackService.timeout(timeout);
    }

    /**
     * Hystrix超时降级测试3: 测试3降级的方法, 可以为private
     * @param timeout
     * @return
     */
    private String timeout3Fallback(Integer timeout) {
        return "success";
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

    /**
     * Hystrix多级降级测试: 以直接降级为例
     * @return
     */
    @GetMapping("/sayHi")
    public String sayHi(){
        return iHystrixFallbackService.sayHi();
    }
}
