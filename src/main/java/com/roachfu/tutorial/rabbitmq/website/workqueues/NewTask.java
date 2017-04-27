package com.roachfu.tutorial.rabbitmq.website.workqueues;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 工作队列例子
 */
public class NewTask {

    private static final String QUEUE_NAME = "task.queue";

    private static final String[] strings = {
            "First message.",
            "Second message..",
            "Third message...",
            "Fourth message....",
            "Fifth message....."
    };

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        String[] messages = strings;
        for (String message: messages){
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(" [x] Send '" + message + "'");
        }

        channel.close();
        connection.close();
    }

}
