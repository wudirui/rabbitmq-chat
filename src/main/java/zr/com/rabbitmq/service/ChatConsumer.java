package zr.com.rabbitmq.service;


import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zr.com.rabbitmq.config.RabbitMqConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 类说明：普通的消费者
 */
public class ChatConsumer {
	private Logger logger = LoggerFactory.getLogger(ChatConsumer.class);

	public static void main(String[] args) throws IOException, TimeoutException {
		new ChatConsumer().receiver("zhangrui");
	}

	public String receiver(String routeKey) throws IOException, TimeoutException {
		//创建连接、连接到RabbitMQ
		ConnectionFactory connectionFactory = RabbitMqConfig.getConnectionFactory();
		//创建连接
		Connection connection = connectionFactory.newConnection();
		//创建信道
		Channel channel = connection.createChannel();

		//在信道中设置交换器
		channel.exchangeDeclare(RabbitMqConfig.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		//申明队列（放在消费者中去做）
		String queueName = "zr-mq";
		channel.queueDeclare(queueName, false, false, false, null);

		//绑定：将队列(queuq-king)与交换器通过 路由键 绑定(king)
		//String routeKey = "zhangrui";
		channel.queueBind(queueName, RabbitMqConfig.EXCHANGE_NAME, routeKey);
		System.out.println("waiting for message ......");

		//申明一个消费者
		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
				String msg = new String(bytes, "UTF-8");

				System.out.println(new String(bytes, "UTF-8"));
			}
		};
		//消息者正是开始在指定队列上消费。(queue-king)
		//TODO 这里第二个参数是自动确认参数，如果是true则是自动确认
		channel.basicConsume(queueName, true, consumer);
		return "";
	}

}
