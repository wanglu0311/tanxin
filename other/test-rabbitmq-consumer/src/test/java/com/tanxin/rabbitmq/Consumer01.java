package com.tanxin.rabbitmq;

import com.rabbitmq.client.*;

import javax.management.Query;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Consumer01
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/13 14:30
 */
public class Consumer01 {

    private static final String QUEUE = "hello";


    public static void main(String[] args) throws IOException, TimeoutException {

        //通过连接工厂创建新的连接和MQ建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机  一个mq服务可以设置多个虚拟机 每个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;


        //建立新连接
        connection = connectionFactory.newConnection();
        //创建会话通道
        Channel channel = connection.createChannel();


        channel.queueDeclare(QUEUE,true,false,false,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            /**
             * @param consumerTag:  标识消费者,可在监听队列时设置
             * @param envelope:
             * @param properties:
             * @param body:
             * @return: void
             * @author: 禄
             * @date: 2020/11/13 14:36
             * @description:
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 交换机
                String s = envelope.getExchange();

                long deliveryTag = envelope.getDeliveryTag();

                //消息内容
                String mes = new String(body,"UTF-8");

                System.out.println("mes = " + mes);
            }
        };

        //监听队列
        /**
         *      1  队列名称
                2  自动回复
                3  消费方法
          */
         channel.basicConsume(QUEUE,true,defaultConsumer);


    }


}
