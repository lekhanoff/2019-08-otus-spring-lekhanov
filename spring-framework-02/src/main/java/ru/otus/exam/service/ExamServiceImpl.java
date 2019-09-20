package ru.otus.exam.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;

@Service
public class ExamServiceImpl implements ExamService {

    private final QuestionDao questionDao;
    
    private final int minimumPassRate;

    private final MessageSource messageSource;

    public ExamServiceImpl(QuestionDao questionDao, @Value("${minimum.pass.rate}") int minimumPassRate, MessageSource messageSource) {
        this.questionDao = questionDao;
        this.minimumPassRate = minimumPassRate;
        this.messageSource = messageSource;
    }

    public ExamResult examine(Scanner scanner) throws IOException {
        List<Question> questionList = questionDao.getQuestions();
        int correctAnswersCount = 0;
        
        for (Question question : questionList) {
            System.out.print(messageSource.getMessage("question.user", null, Locale.getDefault()));
            System.out.println(messageSource.getMessage(question.getQuestion(), null, Locale.getDefault()));
            System.out.print(messageSource.getMessage("answer.user", null, Locale.getDefault()));
            String answer = scanner.nextLine();
            String correctAnswer = messageSource.getMessage(question.getCorrectAnswer(), null, Locale.getDefault());
            if(correctAnswer.equalsIgnoreCase(answer)) {
                correctAnswersCount++;
            } 
        }

        ExamResult examResult = new ExamResult(correctAnswersCount, minimumPassRate);
        
        return examResult;
    }

}
