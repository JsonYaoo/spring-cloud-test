package com.imooc.springcloud.controller;

import com.imooc.springcloud.service.IService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Feign测试前端控制器: 作为服务消费者
 */
@RestController
public class FeignConsumerController {

    @Resource
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi(){
        return iService.sayHi();
    }
}
