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
     * Guava限流组件: 单机版, 只在当前类/当前主机内限流, 不可作用于分布式限流, 但可用于对单机资源进行限制(比如计算量的接口可以进行限流)
     */
    RateLimiter rateLimiter = RateLimiter.create(2.0);

    /**
     * 测试同步非阻塞限流: 初始访问存在预热令牌, 立即成功; 其余时匀速产生2个令牌/1s, 获取不到则快速失败
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
     * 测试限定时间的同步非阻塞限流: 初始访问存在预热令牌, 立即成功; 其余时匀速产生2个令牌/1s, 10个/5s会同步阻塞, 100个/5s则会立即返回失败
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

    /**
     * 测试同步阻塞限流: 当令牌不足够时, 会同步阻塞获取令牌个数, 直到令牌足够才返回
     * @param count
     * @return
     */
    @GetMapping("/acquire")
    public String acquire(Integer count){
        rateLimiter.acquire(count);// 0.0 if not rate-limited
        log.info("success, rate is {}", rateLimiter.getRate());
        return "success";
    }
}
