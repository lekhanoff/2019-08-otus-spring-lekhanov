package ru.otus.lib.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String>{

    List<Book> findByAuthorId(String authorId);

    List<Book> findByGenreId(String genreId);

}
