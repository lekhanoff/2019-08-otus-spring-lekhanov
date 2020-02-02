package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.otus.lib.domain.Author;
import ru.otus.lib.repository.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorDao;
    
    @Override
    public Optional<Author> getById(Long authorId) {
        return authorDao.findById(authorId);
    }
    
    @Override
    public List<Author> getAll() {
        return authorDao.findAll();
    }
}
