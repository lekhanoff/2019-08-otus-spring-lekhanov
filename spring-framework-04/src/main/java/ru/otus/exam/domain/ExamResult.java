package ru.otus.exam.domain;

import java.util.Locale;

import org.springframework.context.MessageSource;

import lombok.Data;

@Data
public class ExamResult {

    private final int correctAnswersCount;
    
    private final int minimumPassRate;

    private final MessageSource messageSource;
    
    public ExamResult(int correctAnswersCount, int minimumPassRate, MessageSource messageSource) {
        this.correctAnswersCount = correctAnswersCount;
        this.minimumPassRate = minimumPassRate;
        this.messageSource = messageSource;
    }
    
    public boolean isPassed() {
        return correctAnswersCount >= minimumPassRate;
    }
    
    public String getResultMessage(String studentName) {
        String resultMessage = null;
        if (isPassed()) {
            resultMessage = messageSource.getMessage("congratulation.message",
                    new Object[] { studentName, correctAnswersCount }, Locale.getDefault());
        } else {
            resultMessage = messageSource.getMessage("fail.message",
                    new Object[] { studentName, correctAnswersCount }, Locale.getDefault());
        }

        return resultMessage;
    }
}
