package ru.otus.lib.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String>{

}
