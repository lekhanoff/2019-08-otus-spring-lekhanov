package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.lib.domain.BookComment;

@Repository
@Transactional
public class BookCommentRepositoryImpl implements BookCommentRepository{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public BookComment createBookComment(BookComment bookComment) {
        em.persist(bookComment);
        return bookComment;
    }

    @Override
    public BookComment updateBookComment(BookComment bookComment) {
        em.merge(bookComment);
        return bookComment;
    }

    @Override
    public void deleteBookComment(Long bookCommentId) {
        BookComment bookComment = em.find(BookComment.class, bookCommentId);
        em.remove(bookComment);
    }

    @Override
    public Optional<BookComment> getById(Long bookCommentId) {
        BookComment bookComment = em.find(BookComment.class, bookCommentId);
        return Optional.ofNullable(bookComment);
    }

    @Override
    public Optional<BookComment> getByBookAndUser(Long bookId, String userName) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book.bookId = :bookId and c.userLogin = :userName", BookComment.class);
        query.setParameter("bookId", bookId);
        query.setParameter("userName", userName);
        
        return query.getResultStream().findFirst();
    }
    
    @Override
    public List<BookComment> getCommentsByBookId(Long bookId) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book.bookId = :bookId", BookComment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

}
