package ru.otus.exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("application")
@Getter
@Setter
public class ApplicationSettings {

    private String locale;
    
    private String questionsPath;
    
    private int minimumPassRate;
}
