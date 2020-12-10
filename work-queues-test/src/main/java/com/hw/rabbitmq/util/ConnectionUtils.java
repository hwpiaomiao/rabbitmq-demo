package com.hw.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author huang.wen
 * created on 2020/12/10
 **/
public class ConnectionUtils {
	public static Connection getConnections() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("10.113.97.64");
		//AMQP  5672
		factory.setPort(5672);
		factory.setVirtualHost("/cms");
		factory.setUsername("cmsuser");
		factory.setPassword("cmspwd");
		Connection newConnection = factory.newConnection();
		return newConnection;
	}
}