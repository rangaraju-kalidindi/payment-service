package com.tw.finseta.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Payment application.
 * This class bootstraps the Spring Boot application context and starts the application.
 *
 * @author Ranga Raju
 */
@SpringBootApplication
public class PaymentApplication {

    /**
     * Main method that launches the Payment application.
     * It uses SpringApplication.run to bootstrap the application context.
     *
     * @param args command-line arguments passed during application startup
     */
	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}

}
