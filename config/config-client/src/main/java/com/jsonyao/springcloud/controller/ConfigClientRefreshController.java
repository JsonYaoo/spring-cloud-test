package com.jsonyao.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置中心客户端: 动态刷新配置文件
 */
@RestController
@RequestMapping("/refresh")
// 运行期进行刷新这个Bean, 在下一次方法调用时会对所有上下游依赖重新注入
@RefreshScope
public class ConfigClientRefreshController {

    /**
     * 测试配置中心动态刷新到本地配置文件的配置属性
     */
    @Value("${myWords}")
    private String words;

    /**
     * 测试配置中心使用秘钥进行解密
     */
    @Value("${food}")
    private String food;

    /**
     * 测试配置中心动态刷新到本地配置文件的配置属性
     * @return
     */
    @GetMapping("/words")
    public String getWords(){
        return words;
    }

    /**
     * 测试配置中心使用秘钥进行解密
     * @return
     */
    @GetMapping("/dinner")
    public String dinner(){
        return "May I have on " + food;
    }
}
