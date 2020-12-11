package com.hw.rabbitmq.listener;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
public class DirectRecService1 {
	public static final String TEST_QUEUE = "direct_amqp_queue_1";

	@RabbitListener(queues = TEST_QUEUE)
	public void consumer(Message message, Channel channel) throws IOException {
			String msg = new String(message.getBody());
			if (msg == null) {
				System.out.println("Msg is null");
			}
			System.out.println("Direct Consumer-1 Get msg:" + msg);
			int i=1/0;
			long deliveryTag = message.getMessageProperties().getDeliveryTag();
			channel.basicAck(deliveryTag, false);

	}
}

