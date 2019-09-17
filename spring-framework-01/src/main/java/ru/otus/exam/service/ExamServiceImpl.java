package ru.otus.exam.service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lombok.RequiredArgsConstructor;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;

@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private static final String ANSWER = "Enter your answer: ";

    private static final String QUESTION = "Question: ";

    private final QuestionDao questionDao;
    
    private final int minimumPassRate;
    
    public ExamResult examine(Scanner scanner) throws IOException {
        List<Question> questionList = questionDao.getQuestions();
        int correctAnswersCount = 0;
        
        for (Question question : questionList) {
            System.out.println(QUESTION + question.getQuestion());
            System.out.print(ANSWER);
            String answer = scanner.nextLine();
            if(question.getCorrectAnswer().equalsIgnoreCase(answer)) {
                correctAnswersCount++;
            } 
        }

        ExamResult examResult = new ExamResult(correctAnswersCount, minimumPassRate);
        
        return examResult;
    }

}
