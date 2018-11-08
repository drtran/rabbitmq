package com.drkiettran.rabbitmq.rcvr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	private final Stix2ProcessorService stix2ProcessorService;

	public Receiver(Stix2ProcessorService stix2ProcessorService) {
		this.stix2ProcessorService = stix2ProcessorService;
	}

	public void receiveMessage(String message) {
		logger.info("Received <" + message + ">");
		stix2ProcessorService.parses(message);
		logger.info("Json Doc is being processed ...");
		logger.info("Parsed docs: " + stix2ProcessorService.numberParsedDocs());
	}

}
