package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.ICommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Feign Consumer前端控制器: 基于公共服务接口的下游服务消费者(与接口相同的包路径)
 */
@RestController
public class FeignClientIntfConsumerController1 {

    // 由于iCommonService与FeignClientIntfConsumerApplication1位于相同的包, 能够被Spring管理, 所以能够注入成功
    @Resource
    private ICommonService iCommonService;

    /**
     * Feign接口Get方法测试
     * @return
     */
    @GetMapping("/sayHi")
    public String sayHi() {
        return iCommonService.sayHi();
    }

    /**
     * Feign接口Post方法测试
     * @param friend
     * @return
     */
    @PostMapping("/sayHiPost")
    public Friend sayHiPost(@RequestBody Friend friend) {
        return iCommonService.sayHiPost(friend);
    }

    /**
     * Feign超时与重试测试
     * @param timeout
     * @return
     */
    @GetMapping("/retry")
    public String retry(int timeout) {
        // 超时到最后会抛出: feign.RetryableException
        return iCommonService.retry(timeout);
    }

    /**
     * Feign接口boolean返回值测试
     * @return
     */
    @PostMapping("/boolean")
    public boolean sayBoolean() {
        return iCommonService.sayBoolean();
    }
}
