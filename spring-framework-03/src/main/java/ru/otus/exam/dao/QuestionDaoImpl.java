package ru.otus.exam.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Repository;

import ru.otus.exam.config.ApplicationSettings;
import ru.otus.exam.domain.Question;

@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {

    private final String path;
    
    public QuestionDaoImpl(ApplicationSettings applicationSettings) {
        this.path = applicationSettings.getQuestionsPath();
    }

    public Optional<List<Question>> getQuestions() throws IOException {
        try(
                InputStream is = this.getClass().getResourceAsStream(path);
                InputStreamReader reader = new InputStreamReader(is); 
                CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withDelimiter(';'));
        ){
            List<Question> questionList = new ArrayList<Question>();
            csvParser.getRecords().forEach(record -> questionList.add(Question.builder()
                      .question(record.get(0))
                      .correctAnswer(record.get(1))
                      .build()));
            return Optional.of(questionList);
        } 
        
    }

}
