package com.jsonyao.springcloud.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Guava限流组件测试应用前端控制器
 */
@RestController
@Slf4j
@RequestMapping("/rateLimiter")
public class RateLimiterController {

    /**
     * Guava限流组件: 单机版
     */
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    /**
     * 测试非阻塞限流: 初始访问存在预热令牌, 立即成功; 其余时匀速产生2个令牌/1s, 获取不到则快速失败
     * @param count
     * @return
     */
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count){
        if(rateLimiter.tryAcquire(count)){
            log.info("success, rate is {}", rateLimiter.getRate());
            return "success";
        }else {
            log.info("fail, rate is {}", rateLimiter.getRate());
            return "fail";
        }
    }

    /**
     * 测试限定时间的非阻塞限流: 初始访问存在预热令牌, 立即成功; 其余时匀速产生2个令牌/1s, 10个/5s会同步阻塞, 100个/5s则会立即返回失败
     * @param count
     * @return
     */
    @GetMapping("/tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count, Integer timeout){
        if(rateLimiter.tryAcquire(count, timeout, TimeUnit.SECONDS)){
            log.info("success, rate is {}", rateLimiter.getRate());
            return "success";
        }else {
            log.info("fail, rate is {}", rateLimiter.getRate());
            return "fail";
        }
    }
}
