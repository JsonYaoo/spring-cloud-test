package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.AccessLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义Redis+Lua限流组件测试前端控制器
 */
@RestController
@Slf4j
public class RatelimiterTestController {

    @Autowired
    private AccessLimiter accessLimiter;

    /**
     * 测试服务调用形式的自定义限流组件
     * @return
     */
    @GetMapping("testLimitService")
    public String testLimitService(){
        // 限流testLimitService方法, 1r/1s
        accessLimiter.limitAccess("testLimitService", 1);
        return "success";
    }
}
