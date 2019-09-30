package ru.otus.exam.dao;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import ru.otus.exam.domain.Question;

public interface QuestionDao {

    Optional<List<Question>> getQuestions() throws IOException;
}
