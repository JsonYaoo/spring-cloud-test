package com.jsonyao.springcloud.biz;

import com.jsonyao.springcloud.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stream测试应用: 测试消费者
 */
@Slf4j
// 绑定信道
@EnableBinding(value = {
        Sink.class,
        BroadcastTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ExceptionTopic.class,
        RequeueTopic.class
})
public class StreamConsumer {

    /**
     * 异常重试计数器
     */
    private AtomicInteger count = new AtomicInteger(1);

    /**
     * 快速入门: 测试消费者消费
     * @param payload
     */
    // Stream默认的信道, 用于测试消费者消费
    @StreamListener(Sink.INPUT)
    public void consumer(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }

    /**
     * 测试自定义广播消息
     * @param payload
     */
    @StreamListener(BroadcastTopic.INPUT)
    public void consumerBroadcastTopic(Object payload) {
        log.info("Broadcast message consumed successfully, payload={}", payload);
    }

    /**
     * 测试单播消息
     * @param payload
     */
    @StreamListener(GroupTopic.INPUT)
    public void consumerGroupTopic(Object payload) {
        log.info("Group message consumed successfully, payload={}", payload);
    }

    /**
     * 测试延迟消息
     * @param messageBean
     */
    @StreamListener(DelayedTopic.INPUT)
    public void consumerDelayedTopic(MessageBean messageBean) {
        log.info("Delayed message consumed successfully, payload={}", messageBean.getPayload());
    }

    /**
     * 测试异常重试(单机版), 即在Consumer本地重试, 而不会发回给Rabbitmq
     * @param messageBean
     */
    @StreamListener(ExceptionTopic.INPUT)
    public void consumerExceptionTopic(MessageBean messageBean) {
        log.info("Are you OK?");

        // 由于初始值为1, 所以只会重试2次就成功了
        if(count.incrementAndGet() % 3 == 0){
            log.info("Fine, thank you. And you?");
            // 清0, 下次重试测试时, 由于初始值是0, 当重试次数用完还是有异常, 则会一次性抛出所有异常, 否则如果最终能消费成功, 则不会抛出异常
            count.set(0);
        } else {
            log.info("What's your problem?");
            throw new RuntimeException("I'm not OK!");
        }
    }

    /**
     * 测试Stream应用: 测试异常重试(联机版), 消费者会重新生成把消息投递回队列尾部
     * @param messageBean
     */
    @StreamListener(RequeueTopic.INPUT)
    public void consumerRequeueTopic(MessageBean messageBean) {
        log.info("Are you OK?");

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            // do nothing
        }

        throw new RuntimeException("I'm not OK!");
    }
}
