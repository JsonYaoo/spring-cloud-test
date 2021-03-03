package com.jsonyao.springcloud.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Gateway实现简易秒杀场景: 定时开始, 测试After断言 => 没用Feign接口
 */
@Data
@Builder
public class Product {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品库存
     */
    private Long stock;
}
