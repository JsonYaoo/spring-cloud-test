package com.jsonyao.springcloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * 自定义Redis+Lua限流组件配置类
 */
@Configuration
public class RedisConfiguration {

    /**
     * 配置RedisTemplate
     * @param redisConnectionFactory
     * @return
     */
    @Bean("stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        return new StringRedisTemplate(redisConnectionFactory);
    }

    /**
     * 配置Lua Redis脚本
     * @return
     */
    @Bean("rateLimitLua")
    public DefaultRedisScript loadRedisScript(){
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource("ratelimiter.lua"));
        redisScript.setResultType(java.lang.Boolean.class);
        return redisScript;
    }
}
