package com.imooc.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign测试服务: 作为服务消费者
 */
@FeignClient("eureka-client")
public interface IService {

    /**
     * 测试Fiegn接口远程调用eureka-client服务提供者的方法
     * @return
     */
    @GetMapping("/sayHi")
    String sayHi();
}
