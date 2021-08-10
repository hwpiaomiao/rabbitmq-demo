package com.hw.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.fridujo.rabbitmq.mock.compatibility.MockConnectionFactoryFactory;

/**
 @author huang.wen
 created on 2020/12/14
 */

@Component
public class BeanConfig {
	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(MockConnectionFactoryFactory.build());
	}

}
