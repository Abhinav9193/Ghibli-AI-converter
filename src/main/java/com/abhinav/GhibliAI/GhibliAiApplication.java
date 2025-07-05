package com.abhinav.GhibliAI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GhibliAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GhibliAiApplication.class, args);
	}

}
