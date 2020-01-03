package zr.com.rabbitmq.service;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zr.com.rabbitmq.config.RabbitMqConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChatProducer {
	Logger logger = LoggerFactory.getLogger(ChatProducer.class);

	public void sender(String routeKey, String message) throws IOException, TimeoutException {
		//创建连接、连接到RabbitMQ
		ConnectionFactory connectionFactory = RabbitMqConfig.getConnectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建信道
		Channel channel = connection.createChannel();

		//在信道中设置交换器
		channel.exchangeDeclare(RabbitMqConfig.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		//申明队列（放在消费者中去做）
		//申明路由键\消息体
		channel.basicPublish(RabbitMqConfig.EXCHANGE_NAME, routeKey, null, message.getBytes());
		logger.info("Sent:" + routeKey + ":" + message);
		channel.close();
		connection.close();

	}

	public static void main(String[] args) throws IOException, TimeoutException {
		new ChatProducer().sender("zhangrui", "hello world");
	}


}
