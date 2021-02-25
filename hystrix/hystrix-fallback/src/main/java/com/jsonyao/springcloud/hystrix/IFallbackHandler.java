package com.jsonyao.springcloud.hystrix;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.IHystrixFallbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试HystrixFallback降级服务: 降级接收类
 */
// 降级实现类必须交由Spring管理, 不然会报依赖缺少错误
@Component
@Slf4j
public class IFallbackHandler implements IHystrixFallbackService {

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public Friend sayHiPost(Friend friend) {
        return null;
    }

    @Override
    public String retry(int timeout) {
        return null;
    }

    /**
     * 降级实现
     * @return
     */
    @Override
    public String error() {
        log.info("Fallback: I'm not a black sheep any more.");
        return "Fallback: I'm not a black sheep any more.";
    }
}
