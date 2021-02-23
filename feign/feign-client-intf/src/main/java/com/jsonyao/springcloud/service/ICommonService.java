package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.entity.Friend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 上游接口提供的公共服务接口: 供上游继承实现 & 供下游继承调用
 */
@FeignClient("feign-client")
public interface ICommonService {

    @GetMapping("/sayHi")
    public String sayHi();

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend);

}
