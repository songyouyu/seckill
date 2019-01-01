package com.imooc.seckill.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * rabbitmq 配置
 * @author youyusong
 * @date 2018/12/31
 */
@Configuration
public class MQConfig {

    public static final String SECKILL_QUEUE = "seckill.queue";
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String TOPIC_EXCHANGE = "topicExchage";

    public static final String HEADER_QUEUE = "header.queue";
    public static final String FANOUT_EXCHANGE = "fanoutxchage";
    public static final String HEADERS_EXCHANGE = "headersExchage";

    /**
     * Direct模式 交换机Exchange
     * */
    @Bean
    public Queue queue() {
        return new Queue(SECKILL_QUEUE, true);
    }


    /**
     * Topic模式 交换机Exchange
     * */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }
    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }
    @Bean
    public TopicExchange topicExchage(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * 不同的 key 绑定到不同的队列
     * @return
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with("topic.key1");
    }
    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with("topic.#");
    }


    /**
     * Fanout模式(广播模式) 交换机Exchange
     * */
    @Bean
    public FanoutExchange fanoutExchage(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public Binding FanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
    }
    @Bean
    public Binding FanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
    }


    /**
     * Header模式 交换机Exchange
     * */
    @Bean
    public HeadersExchange headersExchage(){
        return new HeadersExchange(HEADERS_EXCHANGE);
    }
    @Bean
    public Queue headerQueue() {
        return new Queue(HEADER_QUEUE, true);
    }
    @Bean
    public Binding headerBinding() {
        // 只有当 message 中满足这两个 key,value，才会在 mq 里面存入值
        Map<String, Object> map = new HashMap<>(2);
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headerQueue()).to(headersExchage()).whereAll(map).match();
    }
}
