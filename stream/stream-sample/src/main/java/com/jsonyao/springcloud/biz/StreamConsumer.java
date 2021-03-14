package com.jsonyao.springcloud.biz;

import com.jsonyao.springcloud.topic.BroadcastTopic;
import com.jsonyao.springcloud.topic.DelayedTopic;
import com.jsonyao.springcloud.topic.GroupTopic;
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
        Sink.class,
        BroadcastTopic.class,
        GroupTopic.class,
        DelayedTopic.class
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
}
