package com.roachfu.tutorial.rabbitmq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by roach on 2017/4/9.
 */

@Component
public class Foo {

    public void listen(String foo){
        System.out.println("hahaha");
        System.out.println(foo);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        RabbitTemplate rabbitTemplate = ctx.getBean(RabbitTemplate.class);
        rabbitTemplate.convertAndSend("Hello World!");
        Thread.sleep(1000);
    }
}
