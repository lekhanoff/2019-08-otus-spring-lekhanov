package ru.otus.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import org.jline.reader.LineReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.context.MessageSource;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;
import ru.otus.exam.service.ExamService;
import ru.otus.exam.service.ExamServiceImpl;

@DisplayName("Testing ExamService")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExamServiceTest {

    @Mock
    private QuestionDao questionDao;
    
    @Mock
    private MessageSource messageSource;
    
    @Mock
    private ApplicationSettings applicationSettings;

    @Mock
    private LineReader lineReader;
    
    private ExamService examService; 
    
    @BeforeEach
    public void init() throws IOException {
        Mockito.when(questionDao.getQuestions())
                .thenReturn(
                        Optional.of(Arrays.asList(Question.builder().question("test.question01").correctAnswer("test.answer01").build())));
        
        Mockito.when(applicationSettings.getMinimumPassRate()).thenReturn(1);
        
        Mockito.when(messageSource.getMessage("welcome.user", null, Locale.getDefault())).thenReturn("Welcome!");
        Mockito.when(messageSource.getMessage("question.user", null, Locale.getDefault())).thenReturn("Question: ");
        Mockito.when(messageSource.getMessage("answer.user", null, Locale.getDefault())).thenReturn("Enter your answer: ");
        Mockito.when(messageSource.getMessage("test.question01", null, Locale.getDefault())).thenReturn("The capital of Bulgaria is");
        Mockito.when(messageSource.getMessage("test.answer01", null, Locale.getDefault())).thenReturn("Sofia");

        examService = new ExamServiceImpl(questionDao, applicationSettings, messageSource, lineReader);
    }
    
    @DisplayName("Checking that exam is successfully passed")
    @Test
    public void examineSuccessTest() throws IOException {
        Mockito.when(lineReader.readLine()).thenReturn("Sofia");
        
        ExamResult examResult = examService.examine();
        assertEquals(1, examResult.getCorrectAnswersCount());
        assertTrue(examResult.isPassed());
        
        assertEquals(1, Mockito.mockingDetails(questionDao).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }
    
    @DisplayName("Checking that exam is failed")
    @Test
    public void examineFailTest() throws IOException {
        Mockito.when(lineReader.readLine()).thenReturn("Moscow");
        
        ExamResult examResult = examService.examine();
        assertEquals(0, examResult.getCorrectAnswersCount());
        assertFalse(examResult.isPassed());
        
        assertEquals(1, Mockito.mockingDetails(questionDao).getInvocations().size());
        assertEquals(1, Mockito.mockingDetails(lineReader).getInvocations().size());
    }
    
}
