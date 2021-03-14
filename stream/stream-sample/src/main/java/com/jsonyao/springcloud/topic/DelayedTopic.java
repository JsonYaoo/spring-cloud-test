package com.jsonyao.springcloud.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 测试Stream应用: 延迟Topic
 */
public interface DelayedTopic {

    /**
     * 消费者消费的Topic名称
     */
    String INPUT = "delayedTopic-consumer";

    /**
     * 生产者生产的Topic名称
     */
    String OUTPUT = "delayedTopic-producer";

    /**
     * Topic 消费者
     * @return
     */
    @Input(INPUT)
    SubscribableChannel input();

    /**
     * Topic 生产者
     * @return
     */
    // 注意, 这里如果Input和Output如果value相同, 则启动会抛出bean definition with this name already exists异常,
    // 所以, 需要配置不同的通道名称, 然后在配置文件配置他们的destination
    @Output(OUTPUT)
    MessageChannel output();
}
