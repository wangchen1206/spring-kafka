package com.cc.kafka.producer;

import com.cc.kafka.message.Demo06Message;
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
public class Demo06Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo01Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息,并且指定分区。
        return kafkaTemplate.send(Demo06Message.TOPIC, message).get();
    }

    /**
     *  Producer 将相关联的消息发送到 Topic 下的相同的 Partition 即可
     *
     * @param [id]
     * @author wangchen
     * @createDate 2020/9/15
     **/
    public SendResult syncSendOrderly(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo01Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息,并且指定分区。根据 key 的哈希值取模来获取到其在 Topic 下对应的 Partition
        return kafkaTemplate.send(Demo06Message.TOPIC, String.valueOf(id), message).get();
    }

}