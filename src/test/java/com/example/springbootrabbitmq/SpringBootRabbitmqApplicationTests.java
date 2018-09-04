package com.example.springbootrabbitmq;

import com.example.springbootrabbitmq.config.Bconfig;
import com.example.springbootrabbitmq.config.DeviceMqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRabbitmqApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;
@Autowired
private DeviceMqConfig deviceMqConfig;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void initQueue() {
        // 创建设备信息exchange
        TopicExchange topicExchange = new TopicExchange("deviceTopic.exchange", true, false);
        amqpAdmin.declareExchange(topicExchange);
        // 创建设备信息queue
        Queue envQueue = new Queue("device.info",true,false,false);
        amqpAdmin.declareQueue(envQueue);
        // 绑定
        Binding binding = new Binding("device.info", Binding.DestinationType.QUEUE, "deviceTopic.exchange", "device.#", null);
        amqpAdmin.declareBinding(binding);
    }


    @Test
    public void contextLoads() {
//        rabbitTemplate.send(exchange,routingKey,message);

//        rabbitTemplate.convertAndSend(exchange,routingKey,object);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("aaa", 123);
//        rabbitTemplate.convertAndSend("deviceTopic.info", "device.#", stringObjectHashMap);

        rabbitTemplate.convertAndSend(deviceMqConfig.NORMAL_EXCHANGE_NAME, deviceMqConfig.NORMAL_ROUTRING_KEY, stringObjectHashMap);


    }

    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("device.info");
        System.out.println(o.getClass());
        System.out.println(o);
    }


    @Test
    public void guangbo() {
        rabbitTemplate.convertAndSend("exchange.fanout", "at", "lalla");
    }
}
