package ru.otus.lib.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.AuthorDto;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;
    
    @Override
    public List<AuthorDto> getAllAuthors() {
        return authorDao.findAll().stream().map(AuthorDto::toDto).collect(Collectors.toList());
    }
}
