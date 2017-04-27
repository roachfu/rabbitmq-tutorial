package com.roachfu.tutorial.rabbitmq.website.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 打印消息到控制台
 */
public class ReceiveLogToConsole {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 定义exchange，消费者和生产者都要定义。因为并不知道exchange存不存在。
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 获取队列名
        String queryName = channel.queueDeclare().getQueue();
        // 将队列名和exchange进行绑定
        channel.queueBind(queryName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for message and handle it to console . . . ");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received message '" + message + "'");
            }
        };

        channel.basicConsume(queryName, true, consumer);
    }


}
