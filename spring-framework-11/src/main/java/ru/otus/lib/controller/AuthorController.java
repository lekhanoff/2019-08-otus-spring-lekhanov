package ru.otus.lib.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("v1/authors")
    public Flux<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

}
