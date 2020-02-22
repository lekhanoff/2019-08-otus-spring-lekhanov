package ru.otus.lib;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class LibraryApplication {

	public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApplication.class, args);
	}
}
