package com.example.springbootrabbitmq.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Configuration
public class MyAmqpConfig {
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }


    @Autowired
    private Environment env;

    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost(env.getProperty("mq.host"));
//        connectionFactory.setPort(Integer.parseInt(env.getProperty("mq.port").trim()));
//        connectionFactory.setVirtualHost(env.getProperty("mq.vhost").trim());
//        connectionFactory.setUsername(env.getProperty("mq.username").trim());
//        connectionFactory.setPassword(env.getProperty("mq.password").trim());
        ExecutorService service= Executors.newFixedThreadPool(20);//500个线程的线程池
        connectionFactory.setSharedExecutor(service);
        return connectionFactory;
    }

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
        cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

    //定义rabbitmqTemplate
    @Bean
    public RabbitTemplate fixedReplyQRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        template.setExchange(requestExchange().getName());
        template.setRoutingKey("PRE_RPC");
        template.setReplyAddress(requestExchange().getName() + "/" + replyQueue().getName());
        //template.setReceiveTimeout(60000);
        template.setReplyTimeout(60000);
        return template;
    }


    //rabbitmqTemplate监听返回队列的消息
    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory());
        container.setQueues(replyQueue());
        container.setMessageListener(fixedReplyQRabbitTemplate());
        ExecutorService executorService= Executors.newFixedThreadPool(300);  //300个线程的线程池
        container.setTaskExecutor(executorService);
        container.setConcurrentConsumers(200);
        container.setPrefetchCount(5);
        return container;
    }

    //请求队列和交换器绑定
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(requestQueue()).to(requestExchange()).with("PRE_RPC");
    }

    //返回队列和交换器绑定
    @Bean
    public Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(requestExchange())
                .with(replyQueue().getName());
    }

    //请求队列
    @Bean
    public Queue requestQueue() {
        String queueName = env.getProperty("mq.pre.queue");
        boolean durable = Boolean.valueOf(env.getProperty("mq.pre.queue.durable"));
        boolean exclusive = Boolean.valueOf(env.getProperty("mq.pre.queue.exclusive"));
        boolean autoDelete = Boolean.valueOf(env.getProperty("mq.pre.queue.autoDelete"));
        return new Queue(queueName,durable,exclusive,autoDelete);
    }

    //每个应用实例监听的返回队列
    @Bean
    public Queue replyQueue() {
        String queueName = env.getProperty("mq.prereply.queue")+ UUID.randomUUID().toString();
        boolean durable = Boolean.valueOf(env.getProperty("mq.prereply.queue.durable"));
        boolean exclusive = Boolean.valueOf(env.getProperty("mq.prereply.queue.exclusive"));
        boolean autoDelete = Boolean.valueOf(env.getProperty("mq.prereply.queue.autoDelete"));
        return new Queue(queueName,durable,exclusive,autoDelete);
    }

    //交换器
    @Bean
    public DirectExchange requestExchange() {
        return new DirectExchange("PRE_DIRECT_EXCHANGE", false, true);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer){
        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
        ExecutorService service=Executors.newFixedThreadPool(600);
        factory.setTaskExecutor(service);
        factory.setConcurrentConsumers(500);
        factory.setPrefetchCount(5);
        configurer.configure(factory,rabbitConnectionFactory());
        return factory;
    }
}
