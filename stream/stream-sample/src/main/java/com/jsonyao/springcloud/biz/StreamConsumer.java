package com.jsonyao.springcloud.biz;

import com.jsonyao.springcloud.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stream测试应用: 测试消费者
 */
@Slf4j
// 绑定信道, 单存的配置项, 可以在配置类中配置
@EnableBinding(value = {
        Sink.class,
        BroadcastTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ExceptionTopic.class,
        RequeueTopic.class,
        DlqTopic.class,
        FallbackTopic.class
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

    /**
     * 测试死信队列Topic
     * @param messageBean
     */
    @StreamListener(DlqTopic.INPUT)
    public void consumerDlqTopic(MessageBean messageBean) {
        log.info("DLQ: Are you OK?");

        // 由于初始值为1, 所以只会重试2次就成功了
        if(count.incrementAndGet() % 3 == 0){
            // 死信队列重推消息到该队列时, 由于count已经大于3, 则会消费成功
            log.info("DLQ: Fine, thank you. And you?");
        } else {
            // 当重试次数用完还是有异常, 则会一次性抛出所有异常, 进入死信队列, 否则如果最终能消费成功, 则不会抛出异常
            log.info("DLQ: What's your problem?");
            throw new RuntimeException("DLQ: I'm not OK!");
        }
    }

    /**
     * 测试异常降级, 自定义异常逻辑 + 接口升版
     * @param messageBean
     */
    @StreamListener(FallbackTopic.INPUT)
    public void consumerFallbackTopic(MessageBean messageBean, @Header("version") String version) {
        log.info("Fallback: Are you OK?");

        // 接口升版: 可以根据不同的版本走不同的逻辑
        if("1.0".equalsIgnoreCase(version)){
            log.info("Fallback: Fine, thank you. And you?");
        } else if("2.0".equalsIgnoreCase(version)){
            // 当重试次数用完还是有异常, 则会一次性抛出所有异常, 进入具体异常降级逻辑, 否则如果最终能消费成功, 则不会抛出异常
            log.info("Fallback: unsupported version?");
            throw new RuntimeException("Fallback: I'm not OK!");
        } else {
            log.info("Fallback: version={}", version);
        }
    }

    /**
     * 具体异常降级逻辑
     * @param message
     */
    // 当前方法用于处理MQTT消息, inputChannel参数指定了用于接收消息的channel
    @ServiceActivator(inputChannel = "fallback-Topic.fallback-group.errors")
    public void fallback(Message<?> message){
        log.info("fallback entered, message={}", message);
    }
}
