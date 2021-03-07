package com.jsonyao.springcloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 测试网关JWT鉴权: 模拟账户类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 存入Redis中, 需要可序列化
public class Account implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录Token
     */
    private String token;

    /**
     * 刷新Token: Token过期后用于刷新
     */
    private String refreshToken;

}
