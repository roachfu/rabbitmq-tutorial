package com.roachfu.tutorial.rabbitmq.website.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * topic exchange 生产者
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic.log";

    private static final String[] routingKeys = {"kern.critical", "kern.error", "cron.critical", "auth.warn", "cron.info.rolling"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        for (String routingKey : routingKeys) {
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, routingKey.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + routingKey + "'");
        }
    }
}
