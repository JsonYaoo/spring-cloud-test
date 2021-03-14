package com.jsonyao.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Sleuth Trace B应用: 使用主类作为Controller
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@Slf4j
public class SleuthBtraceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SleuthBtraceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @LoadBalanced
    @Bean("restTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("traceB")
    public String traceA(){
        log.info("-------Trace B");
        return "Trace B";
    }
}