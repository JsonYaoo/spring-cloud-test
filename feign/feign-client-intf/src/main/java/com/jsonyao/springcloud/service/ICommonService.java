package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.entity.Friend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 上游接口提供的公共服务接口: 供上游继承实现 & 供下游继承调用 => 合并该俩接口, 但往往提供者还需要提供非Client的服务接口, 以兼容某些下游不是SpringCloud生态
 */
@FeignClient(value = "feign-client")
public interface ICommonService {

    /**
     * Feign接口Get方法测试
     * @return
     */
    @GetMapping("/sayHi")
    public String sayHi();

    /**
     * Feign接口Post方法测试
     * @param friend
     * @return
     */
    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend);

    /**
     * Feign超时与重试测试
     * @param timeout
     * @return
     */
    @GetMapping("/retry")
    public String retry(@RequestParam("timeout") int timeout);

    /**
     * Hystrix直接异常降级测试
     * @return
     */
    @GetMapping("/error")
    public String error();

    /**
     * Hystrix超时降级测试
     * @param timeout
     * @return
     */
    @PostMapping("/timeout")
    public String timeout(@RequestParam("timeout") int timeout);

    /**
     * Feign接口boolean返回值测试
     * @return
     */
    @PostMapping("/boolean")
    public boolean sayBoolean();
}
