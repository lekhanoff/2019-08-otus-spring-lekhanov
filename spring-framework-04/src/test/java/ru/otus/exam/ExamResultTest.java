package ru.otus.exam;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Locale;

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

import ru.otus.exam.domain.ExamResult;

@DisplayName("Testing ExamResult")
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExamResultTest {

    private static final String FAIL_MESSAGE = "Ivan, unfortunately, the test is failed. Number of correct answers: 1.";
    private static final String CONGRATULATION_MESSAGE = "Ivan, congratulations! Test passed successfully! Number of correct answers: 10.";
    
    @Mock
    private MessageSource messageSource;

    @BeforeEach
    public void init() throws IOException {
        Mockito.when(
                messageSource.getMessage("congratulation.message", new Object[] { "Ivan", 10 }, Locale.getDefault()))
                .thenReturn(CONGRATULATION_MESSAGE);
        
        Mockito.when(
                messageSource.getMessage("fail.message", new Object[] { "Ivan", 1 }, Locale.getDefault()))
                .thenReturn(FAIL_MESSAGE);
    }

    
    @DisplayName("ExamResult is passed")
    @Test
    public void checkExamResultPassed() {
        ExamResult examResult = new ExamResult(10, 10, messageSource);
        assertThat(examResult.isPassed());
        assertThat(examResult.getResultMessage("Ivan")).isEqualTo(CONGRATULATION_MESSAGE);
    }
    
    @DisplayName("ExamResult is failed")
    @Test
    public void checkExamResultNotPassed() {
        ExamResult examResult = new ExamResult(1, 5, messageSource);
        assertThat(examResult.isPassed()).isFalse();
        assertThat(examResult.getResultMessage("Ivan")).isEqualTo(FAIL_MESSAGE);
    }

}
