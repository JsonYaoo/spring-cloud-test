package com.jsonyao.springcloud.hystrix;

import com.jsonyao.springcloud.entity.Friend;
import com.jsonyao.springcloud.service.IHystrixFallbackService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试HystrixFallback降级服务: 降级接收类
 */
// 降级实现类必须交由Spring管理, 不然会报依赖缺少错误: 其实继承IHystrixFallbackService并不好, 降级不依赖继承关系的
@Component
@Slf4j
public class IFallbackHandler implements IHystrixFallbackService {

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

    /**
     * 测试多级服务降级: 一级降级, 这里以直接异常降级为例
     * => 测试结果: 多级降级成功, 发现: Hystrix降级流程中, 如果抛出RuntimeException, 是不会被抛出去的, 所以控制台不会显示异常信息
     * @return
     */
    // 经测试, 降级后再调用自己, 首先会多次调用自己, 最后抛出Feign Hystrix异常的: IHystrixFallbackService#sayHi() failed and fallback failed.
    // => exception: 'class java.lang.RuntimeException' occurred in fallback wasn't ignored. => 可见, 如果正常配置多级降级的话, Hystrix确实会忽略其中的RuntimeException
//    @HystrixCommand(fallbackMethod = "sayHi")
    @HystrixCommand(fallbackMethod = "fallback2")
    @Override
    public String sayHi() {
        log.info("fall back again...");
        throw new RuntimeException("black sheep2!");
    }

    /**
     * 测试多级服务降级: 二级降级, 这里以直接异常降级为例
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallback3")
    public String fallback2() {
        log.info("fall back again and again...");
        throw new RuntimeException("black sheep3!");
    }

    /**
     * 测试多级服务降级: 三级降级, 这里以直接异常降级为例
     * @return
     */
    public String fallback3() {
        log.info("Fallback: I'm not a black sheep any more.");
        return "Fallback: I'm not a black sheep any more.";
    }
}
