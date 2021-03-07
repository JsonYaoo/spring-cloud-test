package com.jsonyao.springcloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试网关JWT鉴权: 登录响应类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    /**
     * 模拟账户
     */
    private Account account;

    /**
     * 响应编码
     */
    private Long code;

}
