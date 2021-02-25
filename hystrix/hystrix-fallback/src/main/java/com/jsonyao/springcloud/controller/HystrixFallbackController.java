package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.service.IHystrixFallbackService;
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
     * @return
     */
    @PostMapping("/timeout2")
    public String timeout2(Integer timeout) {
        return iHystrixFallbackService.timeout(timeout);
    }
}
