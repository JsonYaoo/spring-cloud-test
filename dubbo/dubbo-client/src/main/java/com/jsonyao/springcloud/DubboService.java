package com.jsonyao.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * Dubbo测试应用: 商品服务实现类
 */
@Service
@Slf4j
public class DubboService implements IDubboService{

    @Override
    public Product publish(Product prod) {
        log.info("Publishing prod {}", prod.getName());
        return prod;
    }
}
