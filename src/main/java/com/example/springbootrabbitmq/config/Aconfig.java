package com.example.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class Aconfig {
    private final static String mailRoutingKey = "mail_queue_fail";
    @Bean
    public Queue mailQueue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("x-dead-letter-exchange", "dead_letter_exchange");//设置死信交换机
        map.put("x-dead-letter-routing-key", "mail_queue_fail");//设置死信routingKey
        Queue queue = new Queue("mailQueue",true, false, false, map);
        return queue;
    }

    @Bean
    public DirectExchange mailExchange() {
        return new DirectExchange("mailExchange", true, false);
    }

    @Bean
    public Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(mailExchange())
                .with(mailRoutingKey);
    }

    @Bean
    public Queue deadQueue(){
        Queue queue = new Queue("dead", true);
        return queue;
    }



}
