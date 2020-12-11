package com.hw.rabbitmq.service;

import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hw.rabbitmq.config.DirectModelConfig;
import com.hw.rabbitmq.config.FanoutModelConfig;
import com.hw.rabbitmq.config.TopicModelConfig;
import com.hw.rabbitmq.config.WorkQueueConfig;
import lombok.extern.slf4j.Slf4j;

/**
 @author huang.wen
 created on 2020/12/11
 */
@Service
@Slf4j
public class SendMsService {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	//work queue
	public String sendQueue(Object payload) {
		return baseSend("", WorkQueueConfig.TEST_QUEUE, payload, null, null);
	}

	//fanout
	public String sendExchangeForFanout(Object payload, String routingKey) {
		return baseSend(FanoutModelConfig.TEST_EXCHANGE, routingKey, payload, null, null);
	}

	//direct
	public String sendExchangeForDirect(Object payload, String routingKey) {
		return baseSend(DirectModelConfig.TEST_EXCHANGE, routingKey, payload, null, null);
	}

	//topic
	public String sendExchangeForTopic(Object payload, String routingKey) {
		return baseSend(TopicModelConfig.TEST_EXCHANGE, routingKey, payload, null, null);
	}

	//exception test
	public String noExchangeExp(Object payload, String routingKey) {
		return baseSend("no_exchange_exp", routingKey, payload, null, null);
	}

	//exception test
	public String noRoutingKeyExp(Object payload, String routingKey) {
		return baseSend(DirectModelConfig.TEST_EXCHANGE, routingKey, payload, null, null);
	}

	// msg send to  Exchange callback
	private final RabbitTemplate.ConfirmCallback confirmCallback =
		new RabbitTemplate.ConfirmCallback() {
			public void confirm(CorrelationData correlationData, boolean ack, String cause) {
				if (ack) {
					log.info("消息投递到及交换机成功啦！！！");
				} else {
					log.info("消息投递到及交换机失败啦！！");
				}
			}
		};

	// send msg from  Exchange to queue fail callback
	private final RabbitTemplate.ReturnCallback returnCallback =
		new RabbitTemplate.ReturnCallback() {
			public void returnedMessage(Message message, int replyCode, String replyText, String exchange,
				String routingKey) {
				//失败业务逻辑
				log.info("交换机发送消息到队列失败！");
				log.info("message=" + message.toString());
				log.info("replyCode=" + replyCode);
				log.info("replyText=" + replyText);
				log.info("exchange=" + exchange);
				log.info("routingKey=" + routingKey);
			}
		};

	private String baseSend(String exchange, String routingKey, Object payload, String messageId,
		Long messageExpirationTime) {
		if (messageId == null) {
			messageId = UUID.randomUUID().toString();
		}
		String finalMessageId = messageId;
		MessagePostProcessor messagePostProcessor = setMessagePostProcessor(finalMessageId, messageExpirationTime);
		Message message = convertToMessage(payload);
		CorrelationData correlationData = new CorrelationData(finalMessageId);
		rabbitTemplate.setConfirmCallback(this.confirmCallback);
		rabbitTemplate.setReturnCallback(this.returnCallback);
		rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor, correlationData);
		return finalMessageId;
	}

	private Message convertToMessage(Object payload) {
		Message message = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(payload);
			MessageProperties messageProperties = new MessageProperties();
			messageProperties.setContentEncoding(MessageProperties.CONTENT_TYPE_JSON);
			message = new Message(json.getBytes(), messageProperties);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return message;
	}

	private MessagePostProcessor setMessagePostProcessor(final String finalMessageId,
		final Long messageExpirationTime) {
		return new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				message.getMessageProperties().setMessageId(finalMessageId);
				if (!StringUtils.isEmpty(messageExpirationTime)) {
					message.getMessageProperties().setExpiration(messageExpirationTime.toString());
				}
				message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
				return message;
			}
		};
	}
}

