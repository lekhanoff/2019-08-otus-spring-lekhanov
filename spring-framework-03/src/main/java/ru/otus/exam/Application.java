package ru.otus.exam;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import ru.otus.exam.service.StarterService;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
	    ApplicationContext context = SpringApplication.run(Application.class, args);
		
        StarterService starter = context.getBean(StarterService.class);
        starter.startExam();

        ((ConfigurableApplicationContext)context).close();

	}

}
