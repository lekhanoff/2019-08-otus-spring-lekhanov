package ru.otus.exam.service;

import java.io.IOException;

import ru.otus.exam.domain.ExamResult;
import ru.otus.exam.domain.Student;

public interface ExamService {

    Student login() throws IOException;

    ExamResult examine() throws IOException;
    
}
