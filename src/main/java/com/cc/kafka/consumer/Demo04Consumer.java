package com.cc.kafka.consumer;

import com.cc.kafka.message.Demo04Message;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟消息消费失败，消息存储到死信队列
 *
 * @author wangchen
 * @createDate 2020/9/15
 **/
@Component
public class Demo04Consumer {

    private AtomicInteger count = new AtomicInteger(0);

    private Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = Demo04Message.TOPIC,
            groupId = "demo04-consumer-group-" + Demo04Message.TOPIC)
    public void onMessage(Demo04Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // <X> 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("我就是故意抛出一个异常");
    }

}