package ru.otus.lib.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@Component
public class GenresHealthIndicator implements HealthIndicator {

    private final GenreRepository genreRepository;
    
    @Override
    public Health health() {
        int count = genreRepository.findAll().size();
        if(count > 0) {
            return Health.up().status(Status.UP).build();
        } else {
            return Health.down().status(Status.DOWN).build();
        }
    }
}
