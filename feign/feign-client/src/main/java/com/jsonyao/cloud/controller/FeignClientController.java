package com.jsonyao.cloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.ICommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

// 实现公共接口层: 合并Controller和Service接口 => 作为服务提供者
@RestController
@Slf4j
public class FeignClientController implements ICommonService {

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHi() {
        return "This is " + port;
    }

    @Override
    public Friend sayHiPost(Friend friend) {
        log.info("You are " + friend.getName());
        friend.setPort(port);
        return friend;
    }
}
