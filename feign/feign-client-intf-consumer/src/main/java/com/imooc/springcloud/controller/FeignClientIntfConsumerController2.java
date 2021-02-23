package com.imooc.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.ICommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Feign Consumer前端控制器: 基于公共服务接口的下游服务消费者(与接口不同的包路径)
 */
@RestController
public class FeignClientIntfConsumerController2 {

    // 由于iCommonService与FeignClientIntfConsumerApplication1位于不同的包, 不能够直接被Spring管理, 所以还需要指定Feign接口的扫描路径或者重新指定Feign接口
    @Resource
    private ICommonService iCommonService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return iCommonService.sayHi();
    }

    @PostMapping("/sayHiPost")
    public Friend sayHiPost(@RequestBody Friend friend) {
        return iCommonService.sayHiPost(friend);
    }
}
