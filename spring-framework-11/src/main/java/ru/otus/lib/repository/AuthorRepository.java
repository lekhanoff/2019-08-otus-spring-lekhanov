package ru.otus.lib.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
public interface AuthorRepository extends ReactiveMongoRepository<Author, String>{
}
