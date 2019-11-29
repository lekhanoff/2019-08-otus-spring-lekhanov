package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.BookComment;

@Repository
public interface BookCommentRepository extends MongoRepository<BookComment, String>{

    List<BookComment> findByBookId(String bookId);

    Optional<BookComment> findByBookIdAndUserLogin(String bookId, String userLogin);

}
