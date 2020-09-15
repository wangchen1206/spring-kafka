package com.cc.kafka.producer;

import com.cc.kafka.message.Demo07Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送事务消息
 *
 * @author wangchen
 * @createDate 2020/09/15
 */
@Component
@Slf4j
public class Demo07Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public String  syncSendInTransaction(Integer id,Runnable runner){
        return kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<Object, Object, String>() {
            @Override
            public String doInOperations(KafkaOperations<Object, Object> operations) {
                Demo07Message message = new Demo07Message();
                message.setId(id);
                try {
                    SendResult<Object, Object> sendResult = operations.send(Demo07Message.TOPIC, message).get();
                    log.info("[doInOperations][发送编号：[{}] 发送结果：[{}]]", id, sendResult);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                //本地业务逻辑。。。
                runner.run();
                return "success";
            }
        });
    }
}
