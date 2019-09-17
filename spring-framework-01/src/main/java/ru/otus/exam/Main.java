package ru.otus.exam;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.exam.service.StarterService;

public class Main {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        
        StarterService starter = context.getBean(StarterService.class);
        starter.startExam();

        ((ConfigurableApplicationContext)context).close();
    }
}
