package com.nurdinaffandidev.question_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main entry point for the Question Service Spring Boot application.
 *
 * This class bootstraps the application and enables Feign Clients for inter-service communication.
 */
@SpringBootApplication
@EnableFeignClients  // Enables support for Feign clients in this application
public class QuestionServiceApplication {

	/**
	 * Main method to run the Spring Boot application.
	 *
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(QuestionServiceApplication.class, args);
	}

}
