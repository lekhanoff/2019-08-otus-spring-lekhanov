package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.BookComment;

public interface BookCommentService {

    public BookComment createBookComment();

    public BookComment updateBookComment();
    
    public void deleteBookComment();
    
    public Optional<BookComment> getById();

    public List<BookComment> getBookCommentsByBookId();

    public boolean checkBookCommentExists(Long genreId);
}
