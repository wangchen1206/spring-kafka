package com.cc.kafka.consumer;

import com.cc.kafka.message.Demo01Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/09/14
 */
@Component
@Slf4j
public class Demo01Consumer {

    @KafkaListener(topics = Demo01Message.TOPIC,groupId = "demo01-consumer-group-"+Demo01Message.TOPIC)
    public void onMessage(Demo01Message message){
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

}
