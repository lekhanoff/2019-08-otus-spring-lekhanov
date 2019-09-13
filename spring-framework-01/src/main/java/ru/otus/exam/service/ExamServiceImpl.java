package ru.otus.exam.service;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;

@Data
@Builder
@AllArgsConstructor
public class ExamServiceImpl implements ExamService {

    private static final String ANSWER = "Enter your answer: ";

    private static final String QUESTION = "Question:";

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

        boolean isPassed = correctAnswersCount >= minimumPassRate;
        ExamResult examResult = ExamResult.builder().correctAnswersCount(correctAnswersCount).isPassed(isPassed).build();
        
        return examResult;
    }

}
