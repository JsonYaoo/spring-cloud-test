package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.hystrix.IFallbackHandler;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 测试HystrixFallback降级服务
 */
// 允许Bean同名覆盖: 不配置会报"feign-client.FeignClientSpecification"错误, 因为Feign的代理对象名称是使用里面的服务属性拼凑的, 所以可以配置允许Bean同名覆盖
@FeignClient(value = "feign-client", fallback = IFallbackHandler.class)
public interface IHystrixFallbackService extends ICommonService {

}
