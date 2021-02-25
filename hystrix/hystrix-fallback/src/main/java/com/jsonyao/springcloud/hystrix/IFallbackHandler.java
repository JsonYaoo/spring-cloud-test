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

    /**
     * 超时异常降级实现1: 由于Hystrix超时中断线程的异常被捕获住但又不做处理, 看不到效果, 所以重新做一个测试2
     * @param timeout
     * @return
     */
    @Override
    public String retry(int timeout) {
        return "You are late!";
    }

    /**
     * 直接异常降级实现
     * @return
     */
    @Override
    public String error() {
        log.info("Fallback: I'm not a black sheep any more.");
        return "Fallback: I'm not a black sheep any more.";
    }

    /**
     * 超时异常降级实现2: 由于Hystrix超时中断线程的异常被捕获住但又不做处理, 看不到效果, 所以重新做一个测试2
     * @param timeout
     * @return
     */
    @Override
    public String timeout(int timeout) {
        return "You are late!";
    }
}
