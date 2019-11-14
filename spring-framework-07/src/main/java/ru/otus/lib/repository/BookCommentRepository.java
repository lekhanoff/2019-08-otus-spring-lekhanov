package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.BookComment;

@Repository
public interface BookCommentRepository extends JpaRepository<BookComment, Long>{

    public List<BookComment> findByBookBookId(Long bookId);

    public Optional<BookComment> findByBookBookIdAndUserLogin(Long bookId, String userLogin);

}
