package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.lib.domain.Author;

@Repository
@Transactional
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Author createAuthor(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        em.merge(author);
        return author;
    }

    @Override
    public void deleteAuthor(Long authorId) {
        Author author = em.find(Author.class, authorId);
        em.remove(author);
    }

    @Override
    public Optional<Author> getById(Long authorId) {
        Author author = em.find(Author.class, authorId);
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a order by a.authorId asc", Author.class);
        return query.getResultList();
    }
}
