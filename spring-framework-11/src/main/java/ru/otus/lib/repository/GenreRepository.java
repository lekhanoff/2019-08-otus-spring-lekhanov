package ru.otus.lib.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Genre;

@Repository
public interface GenreRepository extends ReactiveMongoRepository<Genre, String>{

    Optional<Genre> findByName(String name);
}
