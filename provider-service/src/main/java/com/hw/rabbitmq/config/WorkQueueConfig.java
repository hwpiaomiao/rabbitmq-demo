package com.hw.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Component
@Slf4j
public class WorkQueueConfig {
	public static final String TEST_QUEUE = "simple_amqp_queue";

	@Bean(TEST_QUEUE)
	public Queue testQueue() {
		return new Queue(TEST_QUEUE, true);
	}
}
