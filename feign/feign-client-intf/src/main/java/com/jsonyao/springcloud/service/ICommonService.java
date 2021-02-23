package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.entity.Friend;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 上游接口提供的公共服务接口: 供上游继承实现 & 供下游继承调用 => 合并该俩接口, 但往往提供者还需要提供非Client的服务接口, 以兼容某些下游不是SpringCloud生态
 */
@FeignClient("feign-client")
public interface ICommonService {

    @GetMapping("/sayHi")
    public String sayHi();

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend);

}
