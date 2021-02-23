package com.imooc.springcloud;

import com.jsonyao.springcloud.service.ICommonService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

/**
 * Feign Consumer应用: 基于公共服务接口的下游服务消费者(与接口不同的包路径)
 */
@SpringBootApplication
// 由于是服务提供者, 需要Eureka注册中心拉取服务
@EnableDiscoveryClient
// 同时还需要EnableFeignClient, 因为要发起服务调用, 使得Feign接口能动态代理出实现类
@EnableFeignClients
// 1. 由于iCommonService与FeignClientIntfConsumerApplication1位于不同的包, 不能够直接被Spring管理, 所以还需要指定Feign接口的扫描路径
//@EnableFeignClients(basePackages = {"com.jsonyao.springcloud"})
public class FeignClientIntfConsumerApplication2 {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FeignClientIntfConsumerApplication2.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    // 2. 由于iCommonService与FeignClientIntfConsumerApplication1位于不同的包, 不能够直接被Spring管理, 所以还需要重新指定Feign接口
    @FeignClient("feign-client")
    public interface MyIService extends ICommonService {

    }
}
