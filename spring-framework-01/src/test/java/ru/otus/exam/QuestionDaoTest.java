package ru.otus.exam;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.Question;

public class QuestionDaoTest {

    @Test
    public void getQuestionsTest() throws IOException {
        QuestionDao questionDao = QuestionDaoImpl.builder().path("/questions.csv").build();
        List<Question> questions = questionDao.getQuestions();
        
        assertEquals(2, questions.size());
        assertEquals(Question.builder().question("The capital of Bulgaria is").correctAnswer("Sofia").build(), questions.get(0));
        assertEquals(Question.builder().question("Surname of the first president of the USA is").correctAnswer("Washington").build(), questions.get(1));
    }
}
