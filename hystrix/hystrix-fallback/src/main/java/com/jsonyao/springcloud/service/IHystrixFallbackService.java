package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.hystrix.IFallbackHandler;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 测试HystrixFallback降级服务
 */
// spring.main.allow-bean-definition-overriding=true: 允许Bean同名覆盖
@FeignClient(value = "feign-client", fallback = IFallbackHandler.class)
public interface IHystrixFallbackService extends ICommonService {

}
