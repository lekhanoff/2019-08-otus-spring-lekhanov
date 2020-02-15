package ru.otus.lib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Genre;

@Repository
@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long>{

    Optional<Genre> findByName(String name);
}
