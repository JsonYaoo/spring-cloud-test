package com.jsonyao.springcloud.biz;

import lombok.Data;

/**
 * Stream测试应用: 消息实体
 */
@Data
public class MessageBean {

    /**
     * 消息体
     */
    private String payload;
}
