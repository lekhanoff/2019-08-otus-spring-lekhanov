package ru.otus.lib.service;

import java.util.List;

import ru.otus.lib.controller.dto.GenreDto;

public interface GenreService {

    List<GenreDto> getAllGenres();
}
