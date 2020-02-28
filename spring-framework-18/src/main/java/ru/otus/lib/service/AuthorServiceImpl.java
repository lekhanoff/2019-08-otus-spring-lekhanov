package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;
    
    @HystrixCommand(fallbackMethod = "getDefaultAuthors")
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorDao.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
    
    public List<AuthorDto> getDefaultAuthors() {
        return Arrays.asList(AuthorDto.builder().id(Long.valueOf(-1)).lastname("Doe").firstname("John").middlename("Jr.").build());
    }
}
