package com.jsonyao.springcloud.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Stream测试应用: 测试消费者
 */
@Slf4j
// 绑定信道
@EnableBinding(value = {
        Sink.class
})
public class StreamConsumer {

    /**
     * 快速入门: 测试消费者消费
     * @param payload
     */
    // Stream默认的信道, 用于测试消费者消费
    @StreamListener(Sink.INPUT)
    public void consumer(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }
}
