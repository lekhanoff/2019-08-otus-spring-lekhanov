package ru.otus.lib.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@Component
public class AuthorHealthIndicator implements HealthIndicator {

    private final AuthorRepository authorRepository;
    
    @Override
    public Health health() {
        int count = authorRepository.findAll().size();
        if(count > 0) {
            return Health.up().status(Status.UP).build();
        } else {
            return Health.down().status(Status.DOWN).build();
        }
    }
}
