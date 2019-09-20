package ru.otus.exam.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import ru.otus.exam.domain.Question;

@Repository("questionDao")
public class QuestionDaoImpl implements QuestionDao {

    private final String path;
    
    public QuestionDaoImpl(@Value("${questions.path}") String path) {
        this.path = path;
    }

    public List<Question> getQuestions() throws IOException {
        InputStream is = this.getClass().getResourceAsStream(path);
        List<Question> questionList = new ArrayList<>(); 
        
        try(
                InputStreamReader reader = new InputStreamReader(is); 
                CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withDelimiter(';'));
        ){
            csvParser.getRecords().forEach(record -> questionList.add(Question.builder()
                      .question(record.get(0))
                      .correctAnswer(record.get(1))
                      .build()));
        } 
        
        
        return questionList;
    }

}
