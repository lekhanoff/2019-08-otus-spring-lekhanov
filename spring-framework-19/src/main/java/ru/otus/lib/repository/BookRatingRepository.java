package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;
import ru.otus.lib.domain.BookRating;

@Api(tags = "book-rating-controller")
@RepositoryRestResource(path = "/v1/book-ratings")
public interface BookRatingRepository extends JpaRepository<BookRating, Long>{
}
