package ru.otus.exam.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Student;

@Service
public class StarterServiceImpl implements StarterService {


    private final ExamService examService;
    
    private final MessageSource messageSource;
    
    private final String locale;

    public StarterServiceImpl(ExamService examService, MessageSource messageSource, ApplicationSettings applicationSettings) {
        this.examService = examService;
        this.messageSource = messageSource;
        this.locale = applicationSettings.getLocale();
    }

    @Override
    public void startExam() throws IOException {
        setLocale();
        
        Scanner scanner = new Scanner(System.in);
        System.out.println(messageSource.getMessage("welcome.user", null, Locale.getDefault()));  
        System.out.print(messageSource.getMessage("enter.lastname", null, Locale.getDefault()));  
        String lastname = scanner.nextLine();
        
        System.out.print(messageSource.getMessage("enter.firstname", null, Locale.getDefault()));  
        String firstname = new String(scanner.nextLine());  
        
        Student student = Student.builder().lastname(lastname).firstname(firstname).build();
        
        ExamResult examResult = examService.examine(scanner);
        
        String resultMessage = null;
        if(examResult.isPassed()) {
            resultMessage = messageSource.getMessage("congratulation.message",
                    new Object[] { student.getFirstname(), examResult.getCorrectAnswersCount() }, Locale.getDefault());
        } else {
            resultMessage = messageSource.getMessage("fail.message",
                    new Object[] { student.getFirstname(), examResult.getCorrectAnswersCount() }, Locale.getDefault());
        }

        System.out.println(resultMessage);  
    }

    private void setLocale() {
        List<String> localeList = Arrays.asList(locale.split("_"));
        if(!localeList.isEmpty() && localeList.size() > 1) {
            Locale.setDefault(new Locale(localeList.get(0), localeList.get(1)));
        }
    }

}
