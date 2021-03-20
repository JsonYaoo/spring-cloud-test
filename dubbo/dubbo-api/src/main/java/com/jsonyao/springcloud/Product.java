package com.jsonyao.springcloud;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Dubbo测试应用: 商品测试类, 注意需要实现序列化接口
 */
@Data
public class Product implements Serializable {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

}
