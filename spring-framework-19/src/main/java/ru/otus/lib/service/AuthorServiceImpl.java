package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;
    
    private final BookService bookService;
    
    @HystrixCommand(fallbackMethod = "getDefaultAuthors", commandKey = "authors")
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorDao.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
    
    @HystrixCommand(fallbackMethod = "defaultSaveAuthor", commandKey = "authors")
    @Override
    public AuthorDto saveAuthor(AuthorDto author) {
        Author savedAuthor = authorDao.save(Author.fromDto(author));
        return AuthorDto.toDto(savedAuthor);
    }

    @HystrixCommand(fallbackMethod = "getDefaultDelete", commandKey = "authors")
    @Override
    public void deleteAuthor(Long authorId) {
        if(bookService.getCountByAuthorId(authorId) == 0) {
            authorDao.deleteById(authorId);
        }
    }

    @HystrixCommand(fallbackMethod = "getDefaultAuthor", commandKey = "authors")
    @Override
    public Optional<AuthorDto> getAuthorById(Long authorId) {
        return Optional.ofNullable(AuthorDto.toDto(authorDao.findById(authorId).orElse(null)));
    }

    @HystrixCommand(fallbackMethod = "getDefaultAuthors", commandKey = "authors")
    @Override
    public List<AuthorDto> getAuthorsByParams(String filter) {
        return authorDao.findByParams(filter).stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
    
    public List<AuthorDto> getDefaultAuthors() {
        return Arrays.asList(AuthorDto.builder().id(Long.valueOf(-1)).lastname("Doe").firstname("John").middlename("Jr.").build());
    }

    public AuthorDto defaultSaveAuthor(AuthorDto author) {
        return getDefaultAuthors().get(0);
    }

    public Optional<AuthorDto> getDefaultAuthor(Long authorId) {
        return Optional.of(getDefaultAuthors().get(0));
    }

    public void getDefaultDelete(Long authorId) {
    }
}
