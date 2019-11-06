package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.lib.domain.Book;

@Repository
@Transactional
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        em.merge(book);
        return book;
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = em.find(Book.class, bookId);
        em.remove(book);
    }

    @Override
    public Optional<Book> getById(Long bookId) {
        Book book = em.find(Book.class, bookId);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b order by b.bookId asc",
                Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.author.authorId = :authorId order by b.bookId asc",
                Book.class);
        query.setParameter("authorId", authorId);
        return query.getResultList();

    }

    @Override
    public List<Book> getBooksByGenreId(Long genreId) {
        TypedQuery<Book> query = em.createQuery(
                "select b from Book b where b.genre.genreId = :genreId order by b.bookId asc",
                Book.class);
        query.setParameter("genreId", genreId);
        return query.getResultList();
    }
}
