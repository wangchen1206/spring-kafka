package com.cc.kafka.producer;

import com.cc.kafka.message.Demo04Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/9/15
 **/
@Component
public class Demo04Producer {

    /**
     * 使用KafkaConfiguration封装的KafkaTemplate
     *
     * @author wangchen
     * @createDate 2020/9/15
     **/
    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo04Message 消息
        Demo04Message message = new Demo04Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo04Message.TOPIC, message).get();
    }

}