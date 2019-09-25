package ru.otus.exam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;
import ru.otus.exam.service.ExamService;
import ru.otus.exam.service.ExamServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ExamServiceTest {

    @Mock
    private QuestionDao questionDao;
    
    @Mock
    private MessageSource messageSource;

    private ExamService examService; 
    
    @Before
    public void init() throws IOException {
        Mockito.when(questionDao.getQuestions())
                .thenReturn(
                        Optional.of(Arrays.asList(Question.builder().question("test.question01").correctAnswer("test.answer01").build())));
        
        Mockito.when(messageSource.getMessage("question.user", null, Locale.getDefault())).thenReturn("Question: ");
        Mockito.when(messageSource.getMessage("answer.user", null, Locale.getDefault())).thenReturn("Enter your answer: ");
        Mockito.when(messageSource.getMessage("test.question01", null, Locale.getDefault())).thenReturn("The capital of Bulgaria is");
        Mockito.when(messageSource.getMessage("test.answer01", null, Locale.getDefault())).thenReturn("Sofia");

        examService = new ExamServiceImpl(questionDao, 1, messageSource);
    }
    
    @Test
    public void examineSuccessTest() throws IOException {
        
        InputStream input = new ByteArrayInputStream("Sofia".getBytes());
        System.setIn(input);
        Scanner scanner = new Scanner(input);

        ExamResult examResult = examService.examine(scanner);
        assertEquals(1, examResult.getCorrectAnswersCount());
        assertTrue(examResult.isPassed());
        assertEquals(1, Mockito.mockingDetails(questionDao).getInvocations().size());
    }
    
    @Test
    public void examineFailTest() throws IOException {
        InputStream input = new ByteArrayInputStream("Moscow".getBytes());
        System.setIn(input);
        Scanner scanner = new Scanner(input);
        
        ExamResult examResult = examService.examine(scanner);
        assertEquals(0, examResult.getCorrectAnswersCount());
        assertFalse(examResult.isPassed());
        assertEquals(1, Mockito.mockingDetails(questionDao).getInvocations().size());
    }
    
}
