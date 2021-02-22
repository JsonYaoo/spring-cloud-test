package com.jsonyao.springcloud.config;

import com.jsonyao.springcloud.rules.ConsistentHashRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon配置类
 */
@Configuration
// 2. 注解方式指定Ribbon某个服务的负载均衡策略(后加载, 优先级高) => 测试自定义的一致性hash负载均衡规则
@RibbonClient(name = "eureka-client", configuration = ConsistentHashRule.class)
public class RibbonConfiguration {

    /**
     * 1. 指定Ribbon全局的负载均衡策略
     * @return
     */
//    @Bean("defaultLBStrategy")
//    public IRule defaultLBStrategy(){
//        // 1. RandomRule: 随机访问服务结点(不靠谱)
//        return new RandomRule();
//    }
}
