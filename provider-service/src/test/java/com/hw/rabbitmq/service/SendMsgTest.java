package com.hw.rabbitmq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hw.rabbitmq.ProviderServiceApplication;

/**
 @author huang.wen
 created on 2020/12/11
 */
//@SpringBootTest(classes = ProviderServiceApplication.class)
//@RunWith(SpringRunner.class)
public class SendMsgTest {
	@Autowired
	private SendMsService rabbitMqService;

	@Test
	public void testWorkQueue() {
		for (int i = 0; i < 10; i++) {
			String s = "Msg-" + i;
			rabbitMqService.sendQueue(s);
		}
	}

	@Test
	public void testFanout() {
		String s = "broadcast";
		rabbitMqService.sendExchangeForFanout(s, "");
	}

	@Test
	public void testDirect() {
		String s = "京东快递";
		String s1 = "顺丰快递";
		rabbitMqService.sendExchangeForDirect(s, "JD");
		rabbitMqService.sendExchangeForDirect(s1, "SF");
	}

	@Test
	public void testTopic() {
		String s = "EMS快递";
		String s1 = "顺丰快递";
		String s2 = "京东快递";
		rabbitMqService.sendExchangeForTopic(s, "EMS.kd");
		rabbitMqService.sendExchangeForTopic(s1, "SF.kd");
		rabbitMqService.sendExchangeForTopic(s2, "JD.kd");

	}

}
