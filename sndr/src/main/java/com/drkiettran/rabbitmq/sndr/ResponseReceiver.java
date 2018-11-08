package com.drkiettran.rabbitmq.sndr;

import javax.sound.midi.Receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResponseReceiver {
	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	public void receiveMessage(String response) {
		logger.info("Received <" + response + ">");
	}

}
