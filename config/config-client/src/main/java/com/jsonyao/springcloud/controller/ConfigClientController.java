package com.jsonyao.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心客户端: 前端控制器
 */
@RestController
public class ConfigClientController {

    /**
     * 测试直接注入配置中心的配置属性
     */
    @Value("${name}")
    private String name;

    /**
     * 测试配置中心注入到本地配置文件的配置属性
     */
    @Value("${myWords}")
    private String words;

    /**
     * 测试直接注入配置中心的配置属性
     * @return
     */
    @GetMapping("/name")
    public String getName(){
        return name;
    }

    /**
     * 测试配置中心注入到本地配置文件的配置属性
     * @return
     */
    @GetMapping("/words")
    public String getWords(){
        return words;
    }
}
