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

import ru.otus.lib.domain.Genre;

@Repository
public class GenreDaoImpl implements GenreDao {

    public static class GenreRowMapper implements RowMapper<Genre>{

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = Genre.builder()
                    .genreId(rs.getLong("genre_id"))
                    .name(rs.getString("name"))
                    .build();
            return genre;
        }
    }
    
    private final NamedParameterJdbcOperations jdbcOperations;
    
    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Genre createGenre(Genre genre) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into genre (name) values (:name)", paramMap, keyHolder);
        Long id = keyHolder.getKey().longValue();
        genre.setGenreId(id);
        return genre;
    }

    @Override
    public Genre updateGenre(Long genreId, Genre genre) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("name", genre.getName())
                .addValue("genre_id", genreId);
        jdbcOperations.update("update genre set name = :name where genre_id = :genre_id", paramMap);
        genre.setGenreId(genreId);
        return genre;
    }

    @Override
    public void deleteGenre(Long genreId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("genre_id", genreId);
        jdbcOperations.update("delete from genre where genre_id = :genre_id", paramMap);
    }

    @Override
    public Optional<Genre> getById(Long genreId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("genre_id", genreId);
        Genre genre = jdbcOperations.queryForObject("select genre_id, name from genre where genre_id = :genre_id", paramMap, new GenreRowMapper());
        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<Genre> getByName(String name) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("name", name);
        Genre genre = jdbcOperations.queryForObject("select genre_id, name from genre where name = :name", paramMap, new GenreRowMapper());
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> getAll() {
        List<Genre> genreList = jdbcOperations.query("select genre_id, name from genre order by genre_id asc", new GenreRowMapper());
        return genreList;
    }


}
