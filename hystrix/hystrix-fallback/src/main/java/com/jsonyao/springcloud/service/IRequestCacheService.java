package com.jsonyao.springcloud.service;

import com.jsonyao.springcloud.entity.Friend;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测试HystrixFallback降级服务: 测试Hystrix RequestCache
 */
@Service
@Slf4j
public class IRequestCacheService {

    // 这里由于ICommonService经Feign动态代理后, 会有多个实现类, 所以根据类型注入时会报错, 所以使用IHystrixFallbackService类型就好(使用primary属性挺麻烦的)
    @Autowired
    private IHystrixFallbackService iHystrixFallbackService;

    /**
     * 使用name作为CacheKey, 只有key同样, 才返回Hystrix上下文缓存:
     * => 配置口令: 121: 1个HystrixRequestContext上下文, 2个Cache注解@CacheResult和@CacheKey, 1个Command注解@HystrixCommand
     * @param name
     * @return
     */
    // 指定服务降级的第二种写法
    @HystrixCommand(commandKey = "requestCache", fallbackMethod = "requestCacheFallback")
    // 注意@CacheResult是作用在@HystrixCommand注定的方法, 所以还需要配置@HystrixCommand
    @CacheResult
    public Friend requestCache(@CacheKey String name){
        log.info("request cache " + name);

        // 测试RequestCache降级服务: 可见, Hystrix是通过开启一个新的线程来实现降级的, 两不冲突 => 测试成功, Hystrix降级时, 会打断@HystrixCommand指定的线程
        if("fallback".equals(name)){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.info("超时被打断啦!!!");
            }
        }

        Friend friend = new Friend();
        friend.setName(name);
        friend = iHystrixFallbackService.sayHiPost(friend);
        log.info("after requesting cache " + name);
        return friend;
    }

    /**
     * 测试RequestCache降级服务: 可见, Hystrix是通过开启一个新的线程来实现降级的, 两不冲突
     * => 测试成功, Hystrix降级时, 会打断@HystrixCommand指定的线程
     * @param name
     * @return
     */
    private Friend requestCacheFallback(String name){
        String str = "I'm not a cache any more";
        log.info(str);

        Friend friend = new Friend();
        friend.setName(str);
        friend.setPort(str);
        return friend;
    }
}
