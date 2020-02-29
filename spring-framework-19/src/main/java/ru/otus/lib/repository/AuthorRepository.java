package ru.otus.lib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
    
    @Query("Select a from Author a where lower(a.lastname) like lower(concat('%', :filter, '%'))" +
            "or lower(a.firstname) like lower(concat('%', :filter, '%')) " + 
            "or lower(a.middlename) like lower(concat('%', :filter, '%')) ")
    List<Author> findByParams(String filter);

}
