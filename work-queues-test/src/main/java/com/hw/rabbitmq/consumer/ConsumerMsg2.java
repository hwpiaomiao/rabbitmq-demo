package com.hw.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hw.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ConsumerMsg2 {
	private static final String QUEUE_NAME = "test_work_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connections = ConnectionUtils.getConnections();
		final Channel channel = connections.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		//Fair distribution
		int prefecthCount=1;
		channel.basicQos(prefecthCount);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
				byte[] body)
				throws IOException {
				String msg = new String(body, "utf-8");

				System.out.println("Consumer 2 get msg:" + msg);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println("[Consumer 2]  done");
					//Manual confirm
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);

	}
}
