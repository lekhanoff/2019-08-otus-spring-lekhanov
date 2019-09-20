package ru.otus.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:application.properties")
@Configuration
public class PropertyConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigBean() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
