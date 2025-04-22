package com.jovan.backend_c2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendC2Application {

	public static void main(String[] args) {
		SpringApplication.run(BackendC2Application.class, args);
	}

}
