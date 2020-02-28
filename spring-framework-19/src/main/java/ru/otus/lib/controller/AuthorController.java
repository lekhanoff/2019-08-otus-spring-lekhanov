package ru.otus.lib.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/v1/authors/{id}")
    public ResponseEntity<AuthorDto> findAuthorById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id).orElse(AuthorDto.builder().build()));
    }
    
    @PostMapping(value = "/v1/authors", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(author));
    }
    
    @PutMapping(value = "/v1/authors/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> modifyAuthor(@PathVariable(name = "id") Long id, @RequestBody AuthorDto author) {
        author.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(authorService.saveAuthor(author));
    }

    @DeleteMapping("/v1/authors/{id}")
    public ResponseEntity<AuthorDto> deleteAuthorById(@PathVariable(name = "id") Long id) {
        ResponseEntity<AuthorDto> responseEntity = ResponseEntity.ok(authorService.getAuthorById(id).orElse(AuthorDto.builder().build()));
        authorService.deleteAuthor(id);
        return responseEntity;
    }
}
