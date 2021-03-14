package com.jsonyao.springcloud.controller;

import com.jsonyao.springcloud.biz.MessageBean;
import com.jsonyao.springcloud.topic.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Stream应用: 前端控制器
 */
@RestController
@Slf4j
public class StreamSampleController {

    @Autowired
    private BroadcastTopic broadcastTopic;

    @Autowired
    private GroupTopic groupTopic;

    @Autowired
    private DelayedTopic delayedTopic;

    @Autowired
    private ExceptionTopic exceptionTopic;

    @Autowired
    private RequeueTopic requeueTopic;

    @Autowired
    private DlqTopic dlqTopic;

    /**
     * 测试广播: 写@RequestParams后, 如果value没变, 但入参名称变了还是可以保持请求报文入参不变, 便于维持前端代码不变
     * @param body
     */
    @PostMapping("send")
    public void sendMessage(@RequestParam(value = "body") String body){
        Message<String> message = MessageBuilder.withPayload(body).build();
        broadcastTopic.output().send(message);
        log.info("发送完毕 {}" + message);
    }

    /**
     * 测试单播
     * @param body
     */
    @PostMapping("sendToGroup")
    public void sendMessageToGroup(@RequestParam(value = "body") String body){
        Message<String> message = MessageBuilder.withPayload(body).build();
        groupTopic.output().send(message);
        log.info("发送完毕 {}" + message);
    }

    /**
     * 测试延迟消息
     * @param body
     */
    @PostMapping("sendDelayedMessage")
    public void sendDelayedMessage(@RequestParam(value = "body") String body,
                                   @RequestParam(value = "seconds") Integer seconds){
        MessageBean msg = new MessageBean();
        msg.setPayload(body);

        log.info("ready to send delayed message");

        // 注意, Rabbitmq延迟消息必须在header里添加x-delay参数: 表示多少ms后延迟队列会对延迟消息进行消费
        Message<MessageBean> message = MessageBuilder
                .withPayload(msg)
                .setHeader("x-delay", 1000 * seconds)
                .build();
        delayedTopic.output().send(message);

        log.info("发送完毕 {}" + message);
    }

    /**
     * 测试异常重试(单机版), 即在Consumer本地重试, 而不会发回给Rabbitmq
     * @param body
     */
    @PostMapping("sendException")
    public void sendException(@RequestParam(value = "body") String body){
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        log.info("ready to send delayed message");

        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        exceptionTopic.output().send(message);
        log.info("发送完毕 {}" + message);
    }

    /**
     * 测试Stream应用: 测试异常重试(联机版), 消费者会重新生成把消息投递回队列尾部
     * @param body
     */
    @PostMapping("requeue")
    public void requeue(@RequestParam(value = "body") String body){
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        log.info("ready to send delayed message");

        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        requeueTopic.output().send(message);
        log.info("发送完毕 {}" + message);
    }

    /**
     * 测试Stream应用: 测试死信队列Topic
     * @param body
     */
    @PostMapping("dlq")
    public void dlq(@RequestParam(value = "body") String body){
        MessageBean msg = new MessageBean();
        msg.setPayload(body);
        log.info("ready to send delayed message");

        Message<MessageBean> message = MessageBuilder.withPayload(msg).build();
        dlqTopic.output().send(message);
        log.info("发送完毕 {}" + message);
    }
}
