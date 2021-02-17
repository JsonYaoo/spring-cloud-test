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

    /**
     * 测试注解形式的自定义限流组件
     * @return
     */
    // 注意! url路径最好不要驼峰表示, 而是用“-”作为连接符连接
    @GetMapping("test-annotation")
    // 注意! 使用该注解记得扫描(com.imooc.springcloud路径), 这里由于都是com.imooc.springcloud路径, RatelimiterTestApplication会自动扫描到, 所以不用配置
    @com.jsonyao.springcloud.annotation.AccessLimiter(limit = 1)
    public String testLimitAnnotation(int arg0, String[] arg1, Object arg2){
        return "success";
    }
}
