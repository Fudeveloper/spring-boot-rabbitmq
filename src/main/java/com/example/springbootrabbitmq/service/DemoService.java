package com.example.springbootrabbitmq.service;

import com.example.springbootrabbitmq.config.Bconfig;
import com.example.springbootrabbitmq.config.DeviceMqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DemoService {


//    @RabbitListener(queues = {"device.info"})
//    public void service(Object object, Channel channel) {
//        channel.basicAck(, false);
//        System.out.println("收到" + object);
//    }



//    @RabbitListener(queues = {"device.info"})
//    public void service(Message message,Channel channel) throws IOException {
//
////        public void service(@Payload Object message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
////        System.out.println(deliveryTag);
//        // 确认收到消息，并处理完成
////        channel.basicAck(deliveryTag,);
//        // 收到消息，但是处理失败
////        channel.basicNack(deliveryTag,false,false);
////        System.out.println(deliveryTag);
//        System.out.println(message.getMessageProperties().getDeliveryTag());
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
//        System.out.println("收到" + message);
//    }

//    @RabbitListener(queues = {"REDIRECT_QUEUE"})
//    public void testReject(Message message,Channel channel) throws IOException {
//        System.out.println(message.getMessageProperties().getDeliveryTag());
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
//        System.out.println("收到" + message);
//    }


    /**
     * 监听替补队列 来验证死信.
     *
     * @param message the message
     * @param channel the channel
     * @throws IOException the io exception  这里异常需要处理
     */
//    @RabbitListener(queues = {DeviceMqConfig.NORMAL_QUEUE})
//    public void redirect(Message message, Channel channel) throws IOException {
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        //拒绝消息，使得消息自动转到死信队列
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//        System.out.println(("dead message  10s 后 消费消息 {}"+new String (message.getBody())));
//    }
}
