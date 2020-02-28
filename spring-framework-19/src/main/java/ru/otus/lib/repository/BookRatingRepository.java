package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.otus.lib.domain.BookRating;

@RepositoryRestResource
public interface BookRatingRepository extends JpaRepository<BookRating, Long>{
}
