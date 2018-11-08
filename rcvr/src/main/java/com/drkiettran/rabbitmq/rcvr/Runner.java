package com.drkiettran.rabbitmq.rcvr;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {
	private static Logger logger = LoggerFactory.getLogger(Runner.class);
	private final RabbitTemplate rabbitTemplate;

	public Runner(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Sending response...");
		rabbitTemplate.convertAndSend(RcvrApplication.topicExchangeNameResponse, "com.drkiettran.rabbitmq",
				"Hello from rcvr app!");
	}

	public void publishes(@Valid String incomingResponse) {
		rabbitTemplate.convertAndSend(RcvrApplication.topicExchangeNameResponse, "com.drkiettran.rabbitmq",
				incomingResponse);
	}

}
