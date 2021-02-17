package com.jsonyao.springcloud.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Nginx限流测试应用前端控制器
 */
@RestController
@Slf4j
@RequestMapping("/nginx")
public class NginxLimiterController {

    /**
     * Nginx限流测试: 请求速率限流
     * 1. 修改host文件 -> www.imooc-training.com = localhost 127.0.0.1
     * 2. 修改nginx -> 将步骤1中的域名，添加到路由规则当中
     * 3. 添加配置项：参考resources文件夹下面的ratelimit.conf
     * @return
     */
    @GetMapping("/nginx")
    public String nginx(){
        log.info("Nginx success");
        return "success";
    }

    /**
     * Nginx限流测试: 连接数限流
     * @return
     */
    @GetMapping("/nginx-conn")
    public String nginxConn(@RequestParam(defaultValue = "0") int secs){
        try {
            // 模拟保持连接时间
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
