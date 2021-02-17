package com.jsonyao.springcloud.aspect;

import com.google.common.collect.Lists;
import com.jsonyao.springcloud.annotation.AccessLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 自定义Redis+Lua限流注解切面: 通过注解调用
 */
@Component
@Aspect
@Slf4j
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DefaultRedisScript<Boolean> rateLimitLua;

    /**
     * 配置切点: 对自定义注解进行aop处理
     */
    @Pointcut("@annotation(com.jsonyao.springcloud.annotation.AccessLimiter)")
    public void cut(){
        log.debug("com.jsonyao.springcloud.aspect.AccessLimiterAspect.cut()");
    }

    /**
     * 配置切点before通知
     */
    @Before("cut()")
    public void before(JoinPoint joinPoint){
        // 1. 通过切点信息获取注解标注的方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 2. 根据方法签名获取其上注解对象
        Method method = methodSignature.getMethod();
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if(annotation == null) return;

        // 3. 根据注解对象获取配置限流属性
        String methodKey = annotation.methodKey();
        int limit = annotation.limit();

        // 4. 如果没有设置methodKey, 则从调用方法签名自定生成一个Key
        if(StringUtils.isEmpty(methodKey)){
            /*4.1 组装methodKey = class.method#paramTypes*/
            methodKey = method.getClass().getName() + method.getName();
            Class<?>[] parameterTypes = method.getParameterTypes();// 方法入参Class对象
            if(parameterTypes != null){
                String paramTypes = Arrays.stream(parameterTypes).map(Class::getName).collect(Collectors.joining(","));
                methodKey += "#" + paramTypes;
            }
        }

        // eg => java.lang.reflect.MethodtestLimitAnnotation#int,[Ljava.lang.String;,java.lang.Object
        log.info("methodKey: " + methodKey);

        // 5. 执行实际限流操作
        limitAccess(methodKey, limit);
    }

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
