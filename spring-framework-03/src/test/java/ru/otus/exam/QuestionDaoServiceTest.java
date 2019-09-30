package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.Question;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionDaoServiceTest {

    @Autowired
    private QuestionDao questionDao;
    
    @Test
    public void getQuestionsTest() throws IOException {
        Optional<List<Question>> questions = questionDao.getQuestions();
        
        assertThat(questions.get())
            .hasSize(2)
            .contains(Question.builder().question("test.question01").correctAnswer("test.answer01").build(), Index.atIndex(0))
            .contains(Question.builder().question("test.question05").correctAnswer("test.answer05").build(), Index.atIndex(1));
    }
}
