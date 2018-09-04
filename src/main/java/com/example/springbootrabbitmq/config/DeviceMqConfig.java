package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
//@Component("deviceMqConfig")
public class DeviceMqConfig {
    // 死信交换机
    public final static String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    // 死信路由键
    public final static String X_DEAD_ROUTING_KET = "x-dead-letter-routing-key";
    // 死信路由键值
    @Value("${device.DEAD_ROUTRING_KEY}")
    public  String DEAD_ROUTRING_KEY;
    // 死信交换机名
    @Value("${device.DEAD_EXCHANGE_NAME}")
    public  String DEAD_EXCHANGE_NAME;

    // 转发到的队列名称（保存死信的队列名称）
    @Value("${device.DEAD_QUEUE}")
    public  String DEAD_QUEUE;

    // 正常交换机名
    @Value("${device.NORMAL_EXCHANGE_NAME}")
    public  String NORMAL_EXCHANGE_NAME;

    // 正常接受消息的队列名称
    @Value("${device.NORMAL_QUEUE}")
    public  String NORMAL_QUEUE;
    // 正常接受消息的路由键
    @Value("${device.NORMAL_ROUTRING_KEY}")
    public  String NORMAL_ROUTRING_KEY;

    @Bean
    public RestTemplate restTemplate  (){
        System.out.println(DEAD_QUEUE);
        return new RestTemplate();
    }

    /**
     * 正常的交换机
     * @return
     */
    @Bean("normalExchange")
    public Exchange normalExchange(){
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE_NAME).durable(true).build();
    }
    /**
     * 声明一个正常队列，并指定死信交换机和死信队列
     * x-dead-letter-exchange   对应  死信交换机
     * x-dead-letter-routing-key  对应 死信队列
     */
    @Bean("normalQueue")
    public Queue normalQueue() {
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put(X_DEAD_LETTER_EXCHANGE, DEAD_EXCHANGE_NAME);
//       x-dead-letter-routing-key    声明 死信路由键
        args.put(X_DEAD_ROUTING_KET, DEAD_ROUTRING_KEY);
        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(args).build();
    }


    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     *
     * @return the exchange
     */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(DEAD_EXCHANGE_NAME).durable(true).build();
    }
    
    /**
     * 定义死信队列转发队列.
     *
     * @return the queue
     */
    @Bean("deadQueue")
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding(NORMAL_QUEUE, Binding.DestinationType.QUEUE, NORMAL_EXCHANGE_NAME, NORMAL_ROUTRING_KEY, null);
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding(DEAD_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE_NAME, DEAD_ROUTRING_KEY, null);
    }
}
