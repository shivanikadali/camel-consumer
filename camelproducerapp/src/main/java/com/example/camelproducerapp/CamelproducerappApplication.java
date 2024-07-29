package com.example.camelproducerapp;

import org.apache.camel.ProducerTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelproducerappApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelproducerappApplication.class, args);
	}

	@Bean
	CommandLineRunner sendMessages(ProducerTemplate producerTemplate) {
		return args -> {
			producerTemplate.sendBody("direct:start", "Hello, this is a test message with Camel!");
		};
	}
}
