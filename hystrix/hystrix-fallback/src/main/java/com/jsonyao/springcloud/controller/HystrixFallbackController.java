package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.service.IHystrixFallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试HystrixFallback降级应用: 前端控制器
 */
@RestController
public class HystrixFallbackController {

    @Autowired
    private IHystrixFallbackService iHystrixFallbackService;

    /**
     * Hystrix降级测试
     * @return
     */
    @GetMapping("/fallback")
    public String fallback() {
        return iHystrixFallbackService.error();
    }
}
