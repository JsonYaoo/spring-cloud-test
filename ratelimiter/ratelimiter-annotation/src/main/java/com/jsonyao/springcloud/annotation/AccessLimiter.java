package com.jsonyao.springcloud.annotation;

import java.lang.annotation.*;

/**
 * 自定义Redis+Lua限流注解: 通过注解调用
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiter {

    /**
     * 被限流方法名称: 非必填, 切面会自动填充方法名称
     * @return
     */
    String methodKey() default "";

    /**
     * 限流速率, 必填
     * @return
     */
    int limit();
}
