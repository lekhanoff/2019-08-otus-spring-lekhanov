package ru.otus.lib.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ru.otus.lib.domain.Genre;

public interface GenreRepository extends MongoRepository<Genre, String>{

    Optional<Genre> findByName(String name);

}
