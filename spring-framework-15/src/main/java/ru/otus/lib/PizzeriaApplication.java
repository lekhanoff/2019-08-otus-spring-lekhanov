package ru.otus.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@IntegrationComponentScan
@EnableIntegration
@SpringBootApplication
public class PizzeriaApplication {

	public static void main(String[] args) {
        SpringApplication.run(PizzeriaApplication.class, args);
	}
}
