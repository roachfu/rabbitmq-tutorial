package com.roachfu.tutorial.rabbitmq.website.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by roach on 2017/4/9.
 */
public class Send {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个通道
        Channel channel = connection.createChannel();
        //指定队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World";
        // 向队列中发送一条消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
