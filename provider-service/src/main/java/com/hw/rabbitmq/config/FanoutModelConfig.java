package com.hw.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
@Slf4j
public class FanoutModelConfig {
	public static final String TEST_EXCHANGE = "fanout_amqp_exchange";

	public static final String TEST_QUEUE_1 = "fanout_amqp_queue_1";

	public static final String TEST_QUEUE_2 = "fanout_amqp_queue_2";

	@Bean(TEST_EXCHANGE)
	public Exchange testExchange() {
		return ExchangeBuilder.fanoutExchange(TEST_EXCHANGE).durable(true).build();
	}

	@Bean(TEST_QUEUE_1)
	public Queue testQueue1() {
		return new Queue(TEST_QUEUE_1, true);
	}

	@Bean(TEST_QUEUE_2)
	public Queue testQueue2() {
		return new Queue(TEST_QUEUE_2, true);
	}
	@Bean
	Binding bindingTestQueue1(@Qualifier(TEST_QUEUE_1) Queue queue,
		@Qualifier(TEST_EXCHANGE) Exchange exchange) {
		return BindingBuilder
			.bind(queue)
			.to(exchange)
			.with("")
			.noargs();
	}

	@Bean
	Binding bindingTestQueue2(@Qualifier(TEST_QUEUE_2) Queue queue,
		@Qualifier(TEST_EXCHANGE) Exchange exchange) {
		return BindingBuilder
			.bind(queue)
			.to(exchange)
			.with("")
			.noargs();
	}

}
