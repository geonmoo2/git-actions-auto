package com.example.sl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.sl", "com.siot.IamportRestClient"})
public class SlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlApplication.class, args);
	}

}