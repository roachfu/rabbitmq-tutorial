package com.roachfu.tutorial.rabbitmq.website.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "direct.log";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        System.out.println(" [*] begin sent message to exchange");
        /* 分别发送两条 info,warn,error基本的消息 */
        channel.basicPublish(EXCHANGE_NAME, "error", null, "[error] - this is first error message.".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn", null, "[warn ] - this is first warn message.".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "info", null, "[info ] - this is first info message.".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "error", null, "[error] - this is second error message.".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn", null, "[warn ] - this is second warn message.".getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "info", null, "[info ] - this is second info message.".getBytes("UTF-8"));
        System.out.println( " [x] done. . . ");

        channel.close();
        connection.close();
    }
}
