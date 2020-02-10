package ru.otus.lib.service;

import java.util.List;

import ru.otus.lib.controller.dto.AuthorDto;

public interface AuthorService {

    List<AuthorDto> getAllAuthors();
}
