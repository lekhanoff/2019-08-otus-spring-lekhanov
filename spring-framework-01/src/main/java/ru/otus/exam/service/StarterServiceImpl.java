package ru.otus.exam.service;

import java.io.IOException;
import java.util.Scanner;

import lombok.RequiredArgsConstructor;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Student;

@RequiredArgsConstructor
public class StarterServiceImpl implements StarterService {

    private static final String FAIL_MESSAGE = "%s, unfortunately, the test is failed. Number of correct answers: %d.";
    private static final String CONGRATULATION_MESSAGE = "%s, congratulations! Test passed successfully! Number of correct answers: %d.";
    private static final String ENTER_FIRSTNAME = "Enter your name: ";
    private static final String ENTER_LASTNAME = "Enter your surname: ";
    private static final String WELCOME = "Welcome to the testing system!";

    private final ExamService examService;
    
    @Override
    public void startExam() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(WELCOME);  
        System.out.print(ENTER_LASTNAME);  
        String lastname = scanner.nextLine();
        
        System.out.print(ENTER_FIRSTNAME);  
        String firstname = new String(scanner.nextLine());  
        
        Student student = Student.builder().lastname(lastname).firstname(firstname).build();
        
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
    }

}
