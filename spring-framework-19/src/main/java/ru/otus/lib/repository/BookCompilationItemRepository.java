package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;
import ru.otus.lib.domain.BookCompilationItem;

@Api(tags = "book-compilation-item-controller")
@RepositoryRestResource(path = "/v1/book-compilation-items")
public interface BookCompilationItemRepository extends JpaRepository<BookCompilationItem, Long>{
}
