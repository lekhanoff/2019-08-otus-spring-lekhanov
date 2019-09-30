package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.Index;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.Question;

@RunWith(MockitoJUnitRunner.class)
public class QuestionDaoTest {

    @Mock
    private ApplicationSettings applicationSettings;
    
    @Before
    public void init() throws IOException {
        Mockito.when(applicationSettings.getQuestionsPath()).thenReturn("/questions.csv");
    }
    
    @Test
    public void getQuestionsTest() throws IOException {
        QuestionDao questionDao = new QuestionDaoImpl(applicationSettings);
        Optional<List<Question>> questions = questionDao.getQuestions();
        
        assertThat(questions.get())
            .hasSize(2)
            .contains(Question.builder().question("test.question01").correctAnswer("test.answer01").build(), Index.atIndex(0))
            .contains(Question.builder().question("test.question05").correctAnswer("test.answer05").build(), Index.atIndex(1));
    }
}
