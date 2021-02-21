package com.jsonyao.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Ribbon Consumer前端控制器测试类
 */
@RestController
public class RibbonConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sayHi")
    public String sayHi() {
        // 使用有@LoadBalanced注解的restTemplate可直接指定方法的服务名称, 而不需要先choose服务
        return restTemplate.getForObject("http://eureka-client/sayHi", String.class);
    }
}
