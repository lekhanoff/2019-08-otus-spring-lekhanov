package ru.otus.exam;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ru.otus.exam.domain.ExamResult;

public class ExamResultTest {

    @Test
    public void checkExamResultPassed() {
        ExamResult examResult = new ExamResult(10, 10);
        assertTrue(examResult.isPassed());
    }
    
    @Test
    public void checkExamResultNotPassed() {
        ExamResult examResult = new ExamResult(1, 5);
        assertFalse(examResult.isPassed());
    }

}
