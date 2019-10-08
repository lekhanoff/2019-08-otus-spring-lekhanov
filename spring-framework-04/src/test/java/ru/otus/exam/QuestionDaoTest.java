package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.Question;

@DisplayName("Testing QuestionDao")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoTest {

    @Mock
    private ApplicationSettings applicationSettings;
    
    @BeforeEach
    public void init() throws IOException {
        Mockito.when(applicationSettings.getQuestionsPath()).thenReturn("/questions.csv");
    }
    
    @DisplayName("Checking that QuestionDao reads questions from file correctly")
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
