package com.hw.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
public class FanoutRecService1 {
	public static final String TEST_QUEUE = "fanout_amqp_queue_1";

	@RabbitListener(queues = TEST_QUEUE)
	public void consumer(Message message, Channel channel) {
		try {
			String msg = new String(message.getBody());
			if (msg == null) {
				System.out.println("Msg is null");
			}
			Thread.sleep(1000);
			System.out.println(" Fanout Consumer-1 Get msg:" + msg);
			//channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

