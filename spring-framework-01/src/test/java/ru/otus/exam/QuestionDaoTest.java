package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.assertj.core.data.Index;
import org.junit.Test;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.Question;

public class QuestionDaoTest {

    @Test
    public void getQuestionsTest() throws IOException {
        QuestionDao questionDao = new QuestionDaoImpl("/questions.csv");
        List<Question> questions = questionDao.getQuestions();
        
        assertThat(questions)
            .hasSize(2)
            .contains(Question.builder().question("The capital of Bulgaria is").correctAnswer("Sofia").build(), Index.atIndex(0))
            .contains(Question.builder().question("Surname of the first president of the USA is").correctAnswer("Washington").build(), Index.atIndex(1));
    }
}
