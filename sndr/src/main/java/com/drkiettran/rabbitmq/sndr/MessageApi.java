package com.drkiettran.rabbitmq.sndr;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.NativeWebRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Validated
@Api(value = "message")
public interface MessageApi {
	default Optional<NativeWebRequest> getRequest() {
		return Optional.empty();
	}

	@ApiOperation(value = "", nickname = "postMessage", notes = "", response = String.class, tags = {})
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = String.class) })
	@RequestMapping(value = "/message", produces = { "application/text" }, method = RequestMethod.POST)
	default ResponseEntity<String> postMessage(@RequestHeader HttpHeaders headers,
			@ApiParam(value = "incoming message", required = true) @Valid @RequestBody String incomingMessage) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
	}

}
