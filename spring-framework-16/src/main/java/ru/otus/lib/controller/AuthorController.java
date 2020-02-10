package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.service.AuthorService;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("v1/authors")
    public ResponseEntity<List<AuthorDto>> findAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

}
