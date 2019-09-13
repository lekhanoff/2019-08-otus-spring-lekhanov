package ru.otus.exam;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Question;
import ru.otus.exam.service.ExamService;
import ru.otus.exam.service.ExamServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ExamServiceTest {

    @Mock
    private QuestionDao questionDao;

    @Before
    public void init() throws IOException {
        Mockito.when(questionDao.getQuestions())
                .thenReturn(
                        Arrays.asList(Question.builder().question("The capital of Bulgaria is").correctAnswer("Sofia").build()));
    }
    
    @Test
    public void examineSuccessTest() throws IOException {
        ExamService examService = ExamServiceImpl.builder().minimumPassRate(1).questionDao(questionDao).build();
        
        InputStream input = new ByteArrayInputStream("Sofia".getBytes());
        System.setIn(input);
        Scanner scanner = new Scanner(input);

        ExamResult examResult = examService.examine(scanner);
        assertEquals(1, examResult.getCorrectAnswersCount());
        assertEquals(true, examResult.isPassed());
    }
    
    @Test
    public void examineFailTest() throws IOException {
        ExamService examService = ExamServiceImpl.builder().minimumPassRate(1).questionDao(questionDao).build();
        
        InputStream input = new ByteArrayInputStream("Moscow".getBytes());
        System.setIn(input);
        Scanner scanner = new Scanner(input);
        
        ExamResult examResult = examService.examine(scanner);
        assertEquals(0, examResult.getCorrectAnswersCount());
        assertEquals(false, examResult.isPassed());
        
    }
    
}
