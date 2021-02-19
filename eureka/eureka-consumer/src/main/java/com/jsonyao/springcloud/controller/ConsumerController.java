package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.entity.Friend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Eureka Consumer测试前端控制类
 */
@RestController
@Slf4j
public class ConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试消费Get服务
     * @return
     */
    @GetMapping("/hello")
    public String hello(){
        ServiceInstance instance = loadBalancerClient.choose("eureka-client");
        if(instance == null){
            return "No available instances";
        }

        // 根据服务名拉取服务信息
        String targetUrl = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url is {}", targetUrl);
        return restTemplate.getForObject(targetUrl, String.class);
    }

    /**
     * 测试消费Post服务
     * @return
     */
    @PostMapping("/hello")
    public Friend helloPost(){
        ServiceInstance instance = loadBalancerClient.choose("eureka-client");
        if(instance == null){
            return null;
        }

        // 根据服务名拉取服务信息
        String targetUrl = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url is {}", targetUrl);

        Friend friendParam = new Friend();
        friendParam.setName("Eureka Consumer");

        return restTemplate.postForObject(targetUrl, friendParam, Friend.class);
    }
}
