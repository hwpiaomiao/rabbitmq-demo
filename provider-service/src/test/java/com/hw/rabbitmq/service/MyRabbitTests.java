package com.hw.rabbitmq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 @author huang.wen
 created on 2020/12/11
 */
@RunWith(SpringRunner.class)
@SpringRabbitTest
public class MyRabbitTests {
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private RabbitAdmin admin;

	@Autowired
	private RabbitListenerEndpointRegistry registry;

	@Test
	public void test() {

	}

}
