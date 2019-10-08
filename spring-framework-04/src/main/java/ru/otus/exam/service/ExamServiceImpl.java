package ru.otus.exam.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.jline.reader.LineReader;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;
import ru.otus.exam.domain.Student;

@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;
    
    private final int minimumPassRate;

    private final MessageSource messageSource;
    
    private LineReader reader;
    
    public ExamServiceImpl(QuestionDao questionDao, ApplicationSettings applicationSettings, MessageSource messageSource, @Lazy LineReader reader) {
        this.questionDao = questionDao;
        this.minimumPassRate = applicationSettings.getMinimumPassRate();
        this.messageSource = messageSource;
        this.reader = reader;
    }

    @Override
    public Student login() throws IOException {
        System.out.println(messageSource.getMessage("welcome.user", null, Locale.getDefault()));  
        String lastname = reader.readLine(messageSource.getMessage("enter.lastname", null, Locale.getDefault()));
        String firstname = reader.readLine(messageSource.getMessage("enter.firstname", null, Locale.getDefault()));
        
        return Student.builder().lastname(lastname).firstname(firstname).build();
    }

    
    public ExamResult examine() throws IOException {
        Optional<List<Question>> questionListOptional = questionDao.getQuestions();
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        
        questionListOptional.ifPresent(list -> list.forEach(question -> {
            System.out.print(messageSource.getMessage("question.user", null, Locale.getDefault()));
            System.out.println(messageSource.getMessage(question.getQuestion(), null, Locale.getDefault()));
            System.out.print(messageSource.getMessage("answer.user", null, Locale.getDefault()));

            String answer = reader.readLine();
            String correctAnswer = messageSource.getMessage(question.getCorrectAnswer(), null, Locale.getDefault());

            if (correctAnswer.equalsIgnoreCase(answer)) {
                correctAnswersCount.getAndIncrement();
            }
        }));

        ExamResult examResult = new ExamResult(correctAnswersCount.get(), minimumPassRate, messageSource);
        
        return examResult;
    }

}
