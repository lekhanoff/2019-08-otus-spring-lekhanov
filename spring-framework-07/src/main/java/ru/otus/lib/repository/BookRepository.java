package ru.otus.lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    public List<Book> findByAuthorAuthorId(Long authorId);

    public List<Book> findByGenreGenreId(Long genreId);

}
