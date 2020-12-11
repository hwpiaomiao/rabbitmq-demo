package com.hw.rabbitmq.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
public class RetryConfig {
	public static final String TEST_EXCHANGE = "retry_amqp_exchange";

	@Bean
	public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
		return new RepublishMessageRecoverer(rabbitTemplate, TEST_EXCHANGE, "retry");
	}

}
