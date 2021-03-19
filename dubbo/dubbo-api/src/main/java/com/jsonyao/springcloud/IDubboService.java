package com.jsonyao.springcloud;

/**
 * Dubbo测试应用: 商品服务类
 */
public interface IDubboService {

    /**
     * 商品发布测试服务
     * @param prod
     * @return
     */
    Product publish(Product prod);

}
