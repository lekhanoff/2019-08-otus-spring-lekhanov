package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends JpaRepository<Author, Long>{
}
