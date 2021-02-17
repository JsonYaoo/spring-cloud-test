package com.jsonyao.springcloud;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

/**
 * 自定义Redis+Lua限流组件: 通过服务调用
 */
@Service
@Slf4j
public class AccessLimiter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DefaultRedisScript<Boolean> rateLimitLua;

    /**
     * 执行实际限流操作
     * @param methodKey
     * @param limit
     */
    public void limitAccess(String methodKey, Integer limit) {
        // 1. 执行Redis + Lua脚本
        Boolean acquired = stringRedisTemplate.execute(rateLimitLua, Lists.newArrayList(methodKey), limit.toString());
        if(!acquired){
            log.error("your access is blocked, methodKey={}", methodKey);
            throw new RuntimeException("Your access is blocked");
        }
    }
}
