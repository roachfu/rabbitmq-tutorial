package com.roachfu.tutorial.rabbitmq.website.fanout;

import com.rabbitmq.client.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接收消息并打印到文件
 */
public class ReceiveLogToFile {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for message and handle it to file. . . ");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                File file = new File("/Users/roach/fuqiang/temp/fanout.log");

                FileOutputStream out = new FileOutputStream(file, true);
                out.write(body);
                out.write(("\r\n").getBytes());
                out.flush();
                out.close();
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }

}
