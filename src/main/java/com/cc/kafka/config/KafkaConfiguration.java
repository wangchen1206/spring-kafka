package com.cc.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/09/15
 */
@Configuration
public class KafkaConfiguration {

    /**
     * Kafka 消息重试
     *
     * @param kafkaTemplate
     * @author wangchen
     * @createDate 2020/9/15
     **/
    @Bean
    @Primary
    public ErrorHandler errorHandler(KafkaTemplate<?, ?> kafkaTemplate) {
        //1.创建 DeadLetterPublishingRecoverer 它负责实现，在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列
        ConsumerRecordRecoverer consumerRecordRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        //2.创建FixedBackOff对象 重试3次，每次固定间隔10秒
        BackOff backOff = new FixedBackOff(10*1000L,3L);
        //3.创建 SeekToCurrentErrorHandler
        return new SeekToCurrentErrorHandler(consumerRecordRecoverer,backOff);
    }


}
