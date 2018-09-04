package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Bconfig {
//    // 死信交换机
//    public final static String X_DEAD_LETTER_EXCHANGE="x-dead-letter-exchange";
//    // 死信路由键
//    public final static String X_DEAD_ROUTING_KET="x-dead-letter-routing-key";
//    // 死信路由键值
//    public final static String DEAD_ROUTRING_KEY="dead";
//    // 死信交换机名
//    public final static String DEAD_EXCHANGE_NAME="deviceTopic.exchange22";
//
//    // 转发到的队列名称（保存死信）
//    public final static String REDIRECT_QUEUE="device.dead";
//    // 正常接受消息的队列名称
//    public final static String NORMAL_QUEUE="device.info";
//    // 正常接受消息的luyoujian
//    public final static String NORMAL_ROUTRING_KEY="device.#";

//    /**
//     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
//     *
//     * @return the exchange
//     */
//    @Bean("deadLetterExchange")
//    public Exchange deadLetterExchange() {
//        return ExchangeBuilder.directExchange(DEAD_EXCHANGE_NAME).durable(true).build();
//    }
//
//    /**
//     * 声明一个死信队列.
//     * x-dead-letter-exchange   对应  死信交换机
//     * x-dead-letter-routing-key  对应 死信队列
//     *
//     * @return the queue
//     */
//    @Bean("deadLetterQueue")
//    public Queue deadLetterQueue() {
//        Map<String, Object> args = new HashMap<>(2);
////       x-dead-letter-exchange    声明  死信交换机
//        args.put(X_DEAD_LETTER_EXCHANGE, DEAD_EXCHANGE_NAME);
////       x-dead-letter-routing-key    声明 死信路由键
//        args.put(X_DEAD_ROUTING_KET, DEAD_ROUTRING_KEY);
//        return QueueBuilder.durable(NORMAL_QUEUE).withArguments(args).build();
//    }
//
//    /**
//     * 定义死信队列转发队列.
//     *
//     * @return the queue
//     */
//    @Bean("deadQueue")
//    public Queue deadQueue() {
//        return QueueBuilder.durable(REDIRECT_QUEUE).build();
//    }
//
//    /**
//     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
//     *
//     * @return the binding
//     */
//    @Bean
//    public Binding deadLetterBinding() {
//        return new Binding(NORMAL_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE_NAME, NORMAL_ROUTRING_KEY, null);
//    }
//
//    /**
//     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
//     *
//     * @return the binding
//     */
//    @Bean
//    public Binding redirectBinding() {
//        return new Binding(REDIRECT_QUEUE, Binding.DestinationType.QUEUE, DEAD_EXCHANGE_NAME, DEAD_ROUTRING_KEY, null);
//    }
}
