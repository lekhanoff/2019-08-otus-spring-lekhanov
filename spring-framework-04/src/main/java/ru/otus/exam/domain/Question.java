package ru.otus.exam.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {

    private String question;
    
    private String correctAnswer;
    
}
