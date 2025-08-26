package com.ideahub.ideahubbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class IdeahubApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdeahubApplication.class, args);
	}

}
