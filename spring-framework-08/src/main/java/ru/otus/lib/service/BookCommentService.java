package ru.otus.lib.service;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.BookComment;

public interface BookCommentService {

    BookComment createBookComment();

    BookComment updateBookComment();
    
    void deleteBookComment();
    
    Optional<BookComment> getById();

    List<BookComment> getByBookId();

    boolean checkBookCommentExists(String genreId);
}
