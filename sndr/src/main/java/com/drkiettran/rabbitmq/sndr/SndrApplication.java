package com.drkiettran.rabbitmq.sndr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SndrApplication {
	static final String topicExchangeName = "rcvr-topic-exchange";

	static final String queueName = "rcvr-queue";

	public static void main(String[] args) {
		SpringApplication.run(SndrApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer webConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
			}

		};
	}
}
