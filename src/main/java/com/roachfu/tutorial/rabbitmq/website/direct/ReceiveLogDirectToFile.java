package com.roachfu.tutorial.rabbitmq.website.direct;

import com.rabbitmq.client.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogDirectToFile {

    private static final String EXCHANGE_NAME = "direct.log";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println(" [*] Waiting for message and handle it to file . . . ");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                File file = new File("/Users/roach/fuqiang/temp/direct.log");

                FileOutputStream out = new FileOutputStream(file, true);
                out.write(body);
                out.write(("\r\n").getBytes());
                out.flush();
                out.close();
            }
        };

        channel.basicConsume(queueName,consumer);
    }
}
