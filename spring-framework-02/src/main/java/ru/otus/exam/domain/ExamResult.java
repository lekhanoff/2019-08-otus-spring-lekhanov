package ru.otus.exam.domain;

import lombok.Data;

@Data
public class ExamResult {

    private final int correctAnswersCount;
    
    private final int minimumPassRate;

    private boolean isPassed;
    
    public ExamResult(int correctAnswersCount, int minimumPassRate) {
        this.correctAnswersCount = correctAnswersCount;
        this.minimumPassRate = minimumPassRate;
        this.isPassed = correctAnswersCount >= minimumPassRate;
    }
    
    
}
