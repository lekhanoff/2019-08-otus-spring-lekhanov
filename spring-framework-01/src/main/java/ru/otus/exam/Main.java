package ru.otus.exam;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Student;
import ru.otus.exam.service.ExamService;

public class Main {

    private static final String FAIL_MESSAGE = "%s, unfortunately, the test is failed. Number of correct answers: %d.";
    private static final String CONGRATULATION_MESSAGE = "%s, congratulations! Test passed successfully! Number of correct answers: %d.";
    private static final String ENTER_FIRSTNAME = "Enter your name: ";
    private static final String ENTER_LASTNAME = "Enter your surname: ";
    private static final String WELCOME = "Welcome to the testing system!";

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        
        Scanner scanner = new Scanner(System.in);
        System.out.println(WELCOME);  
        System.out.print(ENTER_LASTNAME);  
        String lastname = scanner.nextLine();
        
        System.out.print(ENTER_FIRSTNAME);  
        String firstname = new String(scanner.nextLine());  
        
        Student student = Student.builder().lastname(lastname).firstname(firstname).build();
        
        ExamService examService = context.getBean(ExamService.class);
        ExamResult examResult = examService.examine(scanner);
        
        String resultMessage = null;
        if(examResult.isPassed()) {
            resultMessage = String.format(CONGRATULATION_MESSAGE,
                    student.getFirstname(), examResult.getCorrectAnswersCount());
        } else {
            resultMessage = String.format(FAIL_MESSAGE,
                    student.getFirstname(), examResult.getCorrectAnswersCount());
        }

        System.out.println(resultMessage);  

        scanner.close();
        ((ConfigurableApplicationContext)context).close();
    }
}
