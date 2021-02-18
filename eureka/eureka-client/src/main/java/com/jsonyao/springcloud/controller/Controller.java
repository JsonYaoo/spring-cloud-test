package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Eureka Client测试: 前端控制器测试类
 */
@RestController
@Slf4j
public class Controller {

    @Value("${server.port}")
    private String port;

    /**
     * 测试Get请求
     * @return
     */
    @GetMapping("/sayHi")
    public String sayHi() {
        return "This is " + port;
    }

    /**
     * 测试Post请求
     * @param friend
     * @return
     */
    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend) {
        log.info("You are " + friend.getName());
        friend.setPort(port);
        return friend;
    }

}
