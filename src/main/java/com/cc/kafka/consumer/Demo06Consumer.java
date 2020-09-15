package com.cc.kafka.consumer;

import com.cc.kafka.message.Demo06Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 并发消费
 *
 * @author wangchen
 * @createDate 2020/9/15
 **/
@Component
public class Demo06Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 并发消费
     *
     * @param message
     * @author wangchen 
     * @createDate 2020/9/15
     **/
    @KafkaListener(topics = Demo06Message.TOPIC,
            groupId = "demo06-consumer-group-" + Demo06Message.TOPIC,
            concurrency = "2")
    public void onMessage(Demo06Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}