package com.jsonyao.springcloud;

/**
 * 测试网关JWT鉴权: 响应编码
 */
public class AuthResponseCode {

    /**
     * 成功
     */
    public static final Long SUCCESS = 1L;

    /**
     * 密码错误
     */
    public static final Long INCORRECT_PWD = 1000L;

    /**
     * 用户名不存在: 一般生产上都是抛出"用户名或密码错误", 以防止撞库
     */
    public static final Long USER_NOT_FOUND = 1001L;

}
