package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.Index;
import org.junit.Test;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.Question;

public class QuestionDaoTest {

    @Test
    public void getQuestionsTest() throws IOException {
        QuestionDao questionDao = new QuestionDaoImpl("/questions.csv");
        Optional<List<Question>> questions = questionDao.getQuestions();
        
        assertThat(questions.get())
            .hasSize(2)
            .contains(Question.builder().question("test.question01").correctAnswer("test.answer01").build(), Index.atIndex(0))
            .contains(Question.builder().question("test.question05").correctAnswer("test.answer05").build(), Index.atIndex(1));
    }
}
