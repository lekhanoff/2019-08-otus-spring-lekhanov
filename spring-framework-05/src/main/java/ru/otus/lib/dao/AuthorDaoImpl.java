package ru.otus.lib.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ru.otus.lib.domain.Author;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    public static class AuthorRowMapper implements RowMapper<Author>{

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            Author author = Author.builder()
                    .authorId(rs.getLong("author_id"))
                    .lastname(rs.getString("lastname"))
                    .firstname(rs.getString("firstname"))
                    .middlename(rs.getString("middlename"))
                    .build();
            return author;
        }
    }
    
    private final NamedParameterJdbcOperations jdbcOperations;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Author createAuthor(Author author) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("lastname", author.getLastname())
                .addValue("firstname", author.getFirstname())
                .addValue("middlename", author.getMiddlename());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into author (lastname, firstname, middlename) values (:lastname, :firstname, :middlename)", paramMap, keyHolder);
        Long id = keyHolder.getKey().longValue();
        author.setAuthorId(id);
        return author;
    }

    @Override
    public Author updateAuthor(Long authorId, Author author) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("author_id", authorId)
                .addValue("lastname", author.getLastname())
                .addValue("firstname", author.getFirstname())
                .addValue("middlename", author.getMiddlename());
        jdbcOperations.update("update author set lastname = :lastname, firstname = :firstname, middlename = :middlename where author_id = :author_id", paramMap);
        author.setAuthorId(authorId);
        return author;
    }

    @Override
    public void deleteAuthor(Long authorId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("author_id", authorId);
        jdbcOperations.update("delete from author where author_id = :author_id", paramMap);
    }

    @Override
    public Optional<Author> getById(Long authorId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("author_id", authorId);
        Author author = jdbcOperations.queryForObject("select author_id, lastname, firstname, middlename from author where author_id = :author_id", paramMap, new AuthorRowMapper());
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> getAll() {
        List<Author> authorList = jdbcOperations.query("select author_id, lastname, firstname, middlename from author order by author_id asc", new AuthorRowMapper());
        return authorList;
    }

}
