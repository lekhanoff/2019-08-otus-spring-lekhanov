package ru.otus.exam;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ru.otus.exam.domain.ExamResult;

public class ExamResultTest {

    @Test
    public void checkExamResultPassed() {
        ExamResult examResult = new ExamResult(10, 10);
        assertEquals(true, examResult.isPassed());
    }
    
    @Test
    public void checkExamResultNotPassed() {
        ExamResult examResult = new ExamResult(1, 5);
        assertEquals(false, examResult.isPassed());
    }

}
