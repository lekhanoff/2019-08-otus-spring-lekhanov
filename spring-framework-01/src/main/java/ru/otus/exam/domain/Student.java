package ru.otus.exam.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Student {

    private String lastname;
    
    private String firstname;
}
