package ru.otus.exam.dao;

import java.io.IOException;
import java.util.List;

import ru.otus.exam.domain.Question;

public interface QuestionDao {

    List<Question> getQuestions() throws IOException;
}
