package ru.otus.exam.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;

@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;
    
    private final int minimumPassRate;

    private final MessageSource messageSource;
    
    public ExamServiceImpl(QuestionDao questionDao, ApplicationSettings applicationSettings, MessageSource messageSource) {
        this.questionDao = questionDao;
        this.minimumPassRate = applicationSettings.getMinimumPassRate();
        this.messageSource = messageSource;
    }

    public ExamResult examine(Scanner scanner) throws IOException {
        Optional<List<Question>> questionListOptional = questionDao.getQuestions();
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        
        questionListOptional.ifPresent(list -> list.forEach(question -> {
            System.out.print(messageSource.getMessage("question.user", null, Locale.getDefault()));
            System.out.println(messageSource.getMessage(question.getQuestion(), null, Locale.getDefault()));
            System.out.print(messageSource.getMessage("answer.user", null, Locale.getDefault()));

            String answer = scanner.nextLine();
            String correctAnswer = messageSource.getMessage(question.getCorrectAnswer(), null, Locale.getDefault());

            if (correctAnswer.equalsIgnoreCase(answer)) {
                correctAnswersCount.getAndIncrement();
            }
        }));

        ExamResult examResult = new ExamResult(correctAnswersCount.get(), minimumPassRate);
        
        return examResult;
    }

}
