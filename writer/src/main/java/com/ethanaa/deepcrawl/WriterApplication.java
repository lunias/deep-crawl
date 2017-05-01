package com.ethanaa.deepcrawl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.app.rabbit.sink.RabbitSinkConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({RabbitSinkConfiguration.class})
public class WriterApplication {

	public static void main(String[] args) {
		SpringApplication.run(WriterApplication.class, args);
	}
}
