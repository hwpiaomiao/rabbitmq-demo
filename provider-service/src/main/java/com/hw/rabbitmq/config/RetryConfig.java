package com.hw.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
public class RetryConfig {
	public static final String TEST_EXCHANGE = "retry_amqp_exchange";
	public static final String TEST_QUEUE_1 = "retry_amqp_queue_1";

	@Bean(TEST_EXCHANGE)
	public Exchange testExchange() {
		return ExchangeBuilder.directExchange(TEST_EXCHANGE).durable(true).build();
	}

	@Bean(TEST_QUEUE_1)
	public Queue testQueue1() {
		return new Queue(TEST_QUEUE_1, true);
	}


	@Bean("directBindingTest1")
    Binding bindingTest1(@Qualifier(TEST_QUEUE_1) Queue queue,
		@Qualifier(TEST_EXCHANGE) Exchange exchange) {
		return BindingBuilder
			.bind(queue)
			.to(exchange)
			.with("retry")
			.noargs();
	}

	@Bean
	public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate){
		return new RepublishMessageRecoverer(rabbitTemplate, TEST_EXCHANGE, "retry");
	}
}
