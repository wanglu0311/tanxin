package com.tanxin.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer01
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/13 14:07
 */
public class Producer01 {

    private static final String QUEUE = "hello";


    public static void main(String[] args) {

        //通过连接工厂创建新的连接和MQ建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //设置虚拟机  一个mq服务可以设置多个虚拟机 每个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel= null;

        try {
            //建立新连接
            connection = connectionFactory.newConnection();
            //创建会话通道
            channel = connection.createChannel();
            //声明队列
            /**
             *  queue           队列名称
             *  durable         是否持久化
             *  exclusive       是否独占连接  如果为true 可用于临时队列的创建
             *  autoDelete       自动删除
             *
             **/
            channel.queueDeclare(QUEUE,true,false,false,null);
            //发送消息
            channel.basicPublish("",QUEUE,null,"hahahahhaha".getBytes());
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            try {
//                channel.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            }
//            try {
//                connection.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


    }


}
