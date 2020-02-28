package ru.otus.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ru.otus.lib.domain.BookCompilationItem;

@RepositoryRestResource
public interface BookCompilationItemRepository extends JpaRepository<BookCompilationItem, Long>{
}
