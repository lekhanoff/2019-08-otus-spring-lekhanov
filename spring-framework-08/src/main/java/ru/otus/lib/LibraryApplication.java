package ru.otus.lib;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LibraryApplication {

	public static void main(String[] args) throws IOException {
        SpringApplication.run(LibraryApplication.class, args);
	}
}
