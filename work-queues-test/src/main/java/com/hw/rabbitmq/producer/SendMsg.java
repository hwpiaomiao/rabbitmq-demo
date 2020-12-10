package com.hw.rabbitmq.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hw.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class SendMsg {
	public static final String QUEUE_NAME = "test_work_queue";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection connections = ConnectionUtils.getConnections();
		Channel channel = connections.createChannel();

		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		//Fair distribution
		int prefecthCount = 1;
		channel.basicQos(prefecthCount);

		//open confirm
		channel.confirmSelect();

		for (int i = 0; i < 20; i++) {
			String msg = "workQueue msg" + i;
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
			System.out.println("Send msg:" + msg);
			Thread.sleep(i + 20);
		}
		if (!channel.waitForConfirms()) {
			//send failed,we can retry
			System.out.println("msg send failed");
		} else {
			System.out.println("msg send ok");
		}
		channel.close();
		connections.close();

	}
}
