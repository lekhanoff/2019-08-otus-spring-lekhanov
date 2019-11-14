package ru.otus.lib.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.otus.lib.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long>{

    public Optional<Genre> findByName(String name);

}
