package com.drkiettran.rabbitmq.sndr;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("${com.drkiettran.rabbitmq.base-path:/api/v1}")
public class MessageApiController implements MessageApi {
	private final Logger logger = LoggerFactory.getLogger(MessageApiController.class);

	@Autowired
	private Runner runner;

	@Override
	@ApiOperation(value = "", nickname = "postMessage", notes = "", response = String.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class) })
	@RequestMapping(value = "/message", produces = { "application/text" }, method = RequestMethod.POST)
	public ResponseEntity<String> postMessage(@RequestHeader HttpHeaders headers,
			@ApiParam(value = "incoming message", required = true) @Valid @RequestBody String incomingMessage) {
		logger.info("Received incoming message as: '" + incomingMessage + "'");
		
		runner.publishes(incomingMessage);
		HttpHeaders returnHeaders = new HttpHeaders();
		returnHeaders.add("Content-Type", "application/text");
		return ResponseEntity.status(HttpStatus.OK).headers(returnHeaders).body("OKDOK!");
	}
}
