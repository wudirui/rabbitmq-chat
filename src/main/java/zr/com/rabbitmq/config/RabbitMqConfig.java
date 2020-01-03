package zr.com.rabbitmq.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqConfig {

	public final static String EXCHANGE_NAME = "rabbitmq_chat";

	public static ConnectionFactory getConnectionFactory() {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//设置下连接工厂的连接地址(使用默认端口5672)
		connectionFactory.setHost("192.168.10.119");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("mq_zr");
		connectionFactory.setUsername("zr");
		connectionFactory.setPassword("123456");
		return connectionFactory;
	}


}
