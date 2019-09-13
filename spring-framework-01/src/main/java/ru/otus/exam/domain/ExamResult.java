package ru.otus.exam.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExamResult {

    private boolean isPassed;
    
    private int correctAnswersCount;
}
