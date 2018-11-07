package com.drkiettran.rabbitmq.sndr;

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
		logger.info("Sending message...");
		rabbitTemplate.convertAndSend(SndrApplication.topicExchangeName, "com.drkiettran.rabbitmq",
				"Hello from sndr app!");
	}

	public void publishes(@Valid String incomingMessage) {
		rabbitTemplate.convertAndSend(SndrApplication.topicExchangeName, "com.drkiettran.rabbitmq", incomingMessage);
	}

}
