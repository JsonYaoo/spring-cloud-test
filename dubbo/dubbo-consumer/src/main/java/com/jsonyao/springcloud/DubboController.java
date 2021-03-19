package com.jsonyao.springcloud;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dubbo测试应用: 商品服务消费者
 */
@RestController
public class DubboController {

    @Reference(loadbalance = "roundrobin")
    private IDubboService iDubboService;

    /**
     * 测试消费商品服务
     * @param name
     * @return
     */
    @PostMapping("publish")
    public Product publish(@RequestParam("name") String name){
        Product product = new Product();
        product.setName(name);
        return iDubboService.publish(product);
    }
}
