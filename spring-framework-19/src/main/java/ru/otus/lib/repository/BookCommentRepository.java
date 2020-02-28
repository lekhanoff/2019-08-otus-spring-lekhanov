package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;
import ru.otus.lib.domain.BookComment;

@Api(tags = "book-comments-controller")
@RepositoryRestResource(path = "/v1/book-comments")
public interface BookCommentRepository extends JpaRepository<BookComment, Long>{
}
