package ru.otus.lib.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String>{
}
