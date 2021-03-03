package com.jsonyao.cloud.controller;

import com.jsonyao.springcloud.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gateway实现简易秒杀场景: 定时开始, 测试After断言 => 没用Feign接口
 */
@RestController
@Slf4j
@RequestMapping("gateway")
public class SpikeViaGatewayController {

    /**
     * 商品列表: ID-商品
     */
    public static final Map<Long, Product> items = new ConcurrentHashMap<>();

    /**
     * 获取商品详情
     * @param productId
     * @return
     */
    @GetMapping("details")
    public Product getDetails(@RequestParam("productId") Long productId){
        if(!items.containsKey(productId)){
            // 不存在则发布商品
            Product product = Product.builder()
                            .productId(productId)
                            .description("好吃不贵")
                            .stock(100L)
                            .build();

            // 保证线程安全
            items.putIfAbsent(productId, product);
        }
        return items.get(productId);
    }

    /**
     * 秒杀下单
     * @param productId
     * @return
     */
    @PostMapping("placeOrder")
    public String placeOrder(@RequestParam("productId") Long productId){
        Product product = items.get(productId);
        if (product == null) {
            return "Product not found";
        } else if (product.getStock() <= 0L) {
            return "Sold out";
        }

        Long buyCount = new Random().nextLong() % 99;
        if(buyCount <= 0){
            buyCount = 1L;
        }
        synchronized (product) {
            // 双重检查锁, 引用, 可以直接用来判断
            if (product.getStock() <= 0L) {
                return "Sold out";
            }

            // 扣减库存
            Long stock = new Long(product.getStock());
            Long remain = stock - buyCount;
            if(remain < 0){
                remain = 0L;
            }
            product.setStock(remain);
            buyCount = stock - remain;
        }

        return "Order Placed: " + buyCount + "个!";
    }
}