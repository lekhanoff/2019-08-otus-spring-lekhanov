package ru.otus.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class LibraryApplication {

	public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
	}
}
