package ru.otus.lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Book;

@Repository
@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long>{

    List<Book> findByAuthorId(Long authorId);

    List<Book> findByGenreId(Long genreId);
    
    @Query("Select b from Book b where lower(b.title) like lower(concat('%', :filter, '%')) " + 
            "or lower(b.author.lastname) like lower(concat('%', :filter, '%')) "+ 
            "or lower(b.author.firstname) like lower(concat('%', :filter, '%')) " + 
            "or lower(b.author.middlename) like lower(concat('%', :filter, '%')) " + 
            "or lower(b.genre.name) like lower(concat('%', :filter, '%'))")
    List<Book> findByParams(String filter);
}
