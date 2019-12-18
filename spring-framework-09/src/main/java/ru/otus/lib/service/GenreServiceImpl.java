package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.repository.GenreRepository;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreDao;
    
    @Override
    public Optional<Genre> getById(Long genreId) {
        return genreDao.findById(genreId);
    }
    
    @Override
    public List<Genre> getAll() {
        return genreDao.findAll();
    }
}
