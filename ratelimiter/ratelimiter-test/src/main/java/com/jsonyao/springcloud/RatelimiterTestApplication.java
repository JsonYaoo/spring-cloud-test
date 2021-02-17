package com.jsonyao.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 自定义Redis+Lua限流组件测试应用
 */
@SpringBootApplication
public class RatelimiterTestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RatelimiterTestApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
