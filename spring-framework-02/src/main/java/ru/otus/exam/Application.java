package ru.otus.exam;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ru.otus.exam.service.StarterService;

@ComponentScan
@Configuration
public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        
        StarterService starter = context.getBean(StarterService.class);
        starter.startExam();

        ((ConfigurableApplicationContext)context).close();
    }
}
