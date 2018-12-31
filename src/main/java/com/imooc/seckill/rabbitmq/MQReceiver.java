package com.imooc.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author youyusong
 * @date 2018/12/31
 */
@Slf4j
@Service
public class MQReceiver {

    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message) {
        log.info("QUEUE : {}", message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic queue1 message : " + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic queue2 message : " + message);
    }

    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header queue message : " + new String(message));
    }

}
