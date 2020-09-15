package com.cc.resource;

import com.cc.kafka.KafkaDemoApplication;
import com.cc.kafka.producer.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * Description
 *
 * @author wangchen
 * @createDate 2020/09/08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KafkaDemoApplication.class)
@Slf4j
public class KafkaDemoApplicationTests {

    @Autowired
    private Demo01Producer producer;

    @Autowired
    private Demo02Producer producer2;

    @Autowired
    private Demo04Producer producer4;

    @Autowired
    private Demo06Producer producer6;

    @Autowired
    private Demo07Producer producer7;
    @Autowired
    private Demo08Producer producer8;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        //阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testAsyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {
            @Override
            public void onFailure(Throwable e) {
                log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：{}]]", id, result);
            }
        });

        //阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    @Test
    public void testBatchSend() throws InterruptedException {
        log.info("[testBatchASyncSend][开始执行]");
        for (int i = 0; i < 3; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            producer2.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

                @Override
                public void onFailure(Throwable e) {
                    log.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
                }

                @Override
                public void onSuccess(SendResult<Object, Object> result) {
                    log.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
                }

            });

            // 故意每条消息之间，隔离 10 秒
            Thread.sleep(10 * 1000L);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    /**
     * 测试 死信队列
     *
     * @author wangchen
     * @createDate 2020/9/15
     **/
    @Test
    public void testDeadLetter() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult result = producer4.syncSend(id);
        log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    @Test
    public void testConcurrentConsume() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            int id = (int) (System.currentTimeMillis() / 1000);
            SendResult result = producer6.syncSend(id);
//        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    /**
     * 测试顺序发送
     *
     * @param
     * @author wangchen 
     * @createDate 2020/9/15
     **/
    @Test
    public void testSyncSendOrderly() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            int id = 1;
            SendResult result = producer6.syncSendOrderly(id);
            log.info("[testSyncSend][发送编号：[{}] 发送队列：[{}]]", id, result.getRecordMetadata().partition());
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }



    @Test
    public void testSyncSendInTransaction() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer7.syncSendInTransaction(id, new Runnable() {

            @Override
            public void run() {
                log.info("[run][我要开始睡觉了]");
                try {
                    Thread.sleep(10 * 1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("[run][我睡醒了]");
            }

        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

    /**
     * 测试手动消费
     *
     * @author wangchen
     * @createDate 2020/9/15
     **/
    @Test
    public void testManualConsume() throws ExecutionException, InterruptedException {
        for (int id = 1; id <= 2; id++) {
            SendResult result = producer8.syncSend(id);
            log.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
