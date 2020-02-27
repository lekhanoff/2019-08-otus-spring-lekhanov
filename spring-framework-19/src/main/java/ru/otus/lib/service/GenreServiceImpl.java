package ru.otus.lib.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.controller.dto.GenreDto;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreDao;

    @HystrixCommand(fallbackMethod = "getDefaultGenres", commandKey = "genres")
    @Override
    public List<GenreDto> getAllGenres() {
        return genreDao.findAll().stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    @HystrixCommand(fallbackMethod = "defaultSaveGenre", commandKey = "genres")
    @Override
    public GenreDto saveGenre(GenreDto genre) {
        Genre savedGenre = genreDao.save(Genre.fromDto(genre));
        return GenreDto.toDto(savedGenre);
    }

    @HystrixCommand(fallbackMethod = "defaultDeleteGenre", commandKey = "genres")
    @Override
    public void deleteGenre(Long genreId) {
        genreDao.deleteById(genreId);
    }

    @HystrixCommand(fallbackMethod = "getDefaultGenre", commandKey = "genres")
    @Override
    public Optional<GenreDto> getGenreById(Long genreId) {
        return Optional.ofNullable(GenreDto.toDto(genreDao.findById(genreId).orElse(null)));
    }

    @HystrixCommand(fallbackMethod = "getDefaultGenresWithFilter", commandKey = "genres")
    @Override
    public List<GenreDto> getGenresByParams(String filter) {
        return genreDao.findByParams(filter).stream().map(GenreDto::toDto).collect(Collectors.toList());
    }

    public List<GenreDto> getDefaultGenres() {
        return Arrays.asList(GenreDto.builder().id(Long.valueOf(-1)).name("Mystery").build());
    }

    public List<GenreDto> getDefaultGenresWithFilter(String filter) {
        return getDefaultGenres();
    }
    
    public Optional<GenreDto> getDefaultGenre(Long genreId) {
        return Optional.of(getDefaultGenres().get(0));
    }

    public GenreDto defaultSaveGenre(GenreDto genre) {
        return getDefaultGenres().get(0);
    }
    
    public void defaultDeleteGenre(Long genreId) {
    }
}
