package ru.otus.lib.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.otus.lib.domain.Genre;

@Repository
@Transactional
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Genre createGenre(Genre genre) {
        em.persist(genre);
        return genre;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        em.merge(genre);
        return genre;
    }

    @Override
    public void deleteGenre(Long genreId) {
        Genre genre = em.find(Genre.class, genreId);
        em.remove(genre);
    }

    @Override
    public Optional<Genre> getById(Long genreId) {
        Genre genre = em.find(Genre.class, genreId);
        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g order by g.genreId asc", Genre.class);
        return query.getResultList();
    }

}
