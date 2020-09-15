package com.cc.kafka.producer;

import com.cc.kafka.message.Demo01Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/09/14
 */
@Component
public class Demo01Producer {

    @Resource
    private KafkaTemplate<Object,Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        Demo01Message demo01Message = new Demo01Message();
        demo01Message.setId(id);
        //同步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC,demo01Message).get();
    }

    public ListenableFuture<SendResult<Object,Object>> asyncSend(Integer id){
        Demo01Message demo01Message = new Demo01Message();
        demo01Message.setId(id);
        //异步发送消息
        return kafkaTemplate.send(Demo01Message.TOPIC,demo01Message);
    }
}
