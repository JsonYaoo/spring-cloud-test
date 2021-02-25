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

    /**
     * Feign接口Get方法测试
     * @return
     */
    @Override
    public String sayHi() {
        return "This is " + port;
    }

    /**
     * Feign接口Post方法测试
     * @param friend
     * @return
     */
    @Override
    public Friend sayHiPost(Friend friend) {
        log.info("You are " + friend.getName());
        friend.setPort(port);
        return friend;
    }

    /**
     * Feign超时与重试测试
     * @param timeout
     * @return
     */
    @Override
    public String retry(int timeout) {
        while (timeout-- > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
        log.info("retry: " + port);
        return "retry: " + port;
    }

    /**
     * Hystrix降级测试
     * @return
     */
    @Override
    public String error() {
        throw new RuntimeException("black sheep!");
    }
}
