package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.controller.dto.AuthorDto;

public interface AuthorService {

    AuthorDto saveAuthor(AuthorDto author);

    void deleteAuthor(Long authorId);
    
    Optional<AuthorDto> getAuthorById(Long authorId);
    
    List<AuthorDto> getAuthorsByParams(String filter);

    List<AuthorDto> getAllAuthors();
}
