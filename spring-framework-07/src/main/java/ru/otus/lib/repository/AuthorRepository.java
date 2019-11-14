package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
