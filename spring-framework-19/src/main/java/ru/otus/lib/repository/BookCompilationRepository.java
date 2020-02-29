package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import io.swagger.annotations.Api;
import ru.otus.lib.domain.BookCompilation;

@Api(tags = "book-compilation-controller")
@RepositoryRestResource(path = "/v1/book-compilations")
public interface BookCompilationRepository extends JpaRepository<BookCompilation, Long>{
}
