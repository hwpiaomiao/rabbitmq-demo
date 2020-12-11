package com.hw.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hw.rabbitmq.service.SendMsService;

/**
 @author huang.wen
 created on 2020/12/11
 */
@RestController
public class ExceptionTestController {

	@Autowired
	private SendMsService sendMsService;

	@GetMapping("/no-exchange-exp")
	public String noExchangeExp(@RequestParam("msg") String msg) {
		sendMsService.noExchangeExp(msg, "no_routingKey");
		return "ok";
	}

	@GetMapping("/no-routing-key-exp")
	public String noRoutingKeyExp(@RequestParam("msg") String msg) {
		sendMsService.noRoutingKeyExp(msg, "no_routingKey");
		return "ok";
	}
}

