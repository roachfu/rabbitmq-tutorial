package com.roachfu.tutorial.rabbitmq.website.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogDirectToConsole {

    private static final String EXCHANGE_NAME = "direct.log";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "info");
        channel.queueBind(queueName, EXCHANGE_NAME, "warn");
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println(" [*] Waiting for message and handle it to console . . . ");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] receive " + envelope.getRoutingKey() + " : '" + message + "'");
            }
        };

        channel.basicConsume(queueName,consumer);
    }
}
