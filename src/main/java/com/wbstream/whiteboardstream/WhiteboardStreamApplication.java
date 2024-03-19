package com.wbstream.whiteboardstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wbstream.whiteboardstream.*")
public class WhiteboardStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhiteboardStreamApplication.class, args);
	}

}
