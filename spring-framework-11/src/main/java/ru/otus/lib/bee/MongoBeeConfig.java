package ru.otus.lib.bee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;

import ru.otus.lib.bee.changelog.DatabaseChangelog;

@Configuration
public class MongoBeeConfig {
    
    @Autowired
    private MongoClient mongo;
    
    @Value("${spring.data.mongodb.database:reactivelib}")
    private String dbName; 
    
    @Bean
    public Mongobee mongobee(Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName(dbName);
        runner.setChangeLogsScanPackage(DatabaseChangelog.class.getPackage().getName());
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
