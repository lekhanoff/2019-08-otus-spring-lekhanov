package ru.otus.exam.service;

import java.io.IOException;
import java.util.Scanner;

import ru.otus.exam.domain.ExamResult;

public interface ExamService {

    ExamResult examine(Scanner scanner) throws IOException;
    
}
