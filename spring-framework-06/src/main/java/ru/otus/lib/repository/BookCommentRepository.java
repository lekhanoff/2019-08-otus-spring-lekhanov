package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import ru.otus.lib.domain.BookComment;

public interface BookCommentRepository {

    public BookComment createBookComment(BookComment bookComment);

    public BookComment updateBookComment(BookComment bookComment);
    
    public void deleteBookComment(Long bookCommentId);
    
    public Optional<BookComment> getById(Long bookCommentId);

    public List<BookComment> getCommentsByBookId(Long bookId);

    public Optional<BookComment> getByBookAndUser(Long bookId, String userName);


}
