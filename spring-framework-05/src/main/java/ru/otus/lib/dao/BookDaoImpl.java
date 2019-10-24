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
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;

@Repository
public class BookDaoImpl implements BookDao {

    public static class BookRowMapper implements RowMapper<Book>{

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = Book.builder()
                    .bookId(rs.getLong("book_id"))
                    .authorId(rs.getLong("author_id"))
                    .genreId(rs.getLong("genre_id"))
                    .title(rs.getString("title"))
                    .author(Author.builder()
                            .authorId(rs.getLong("author_id"))
                            .lastname(rs.getString("lastname"))
                            .firstname(rs.getString("firstname"))
                            .middlename(rs.getString("middlename"))
                            .build())
                    .genre(Genre.builder().genreId(rs.getLong("genre_id")).name(rs.getString("name")).build())
                    .build();
            return book;
        }
    }
    
    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoImpl(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Book createBook(Book book) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("author_id", book.getAuthorId())
                .addValue("genre_id", book.getGenreId())
                .addValue("title", book.getTitle());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update("insert into book (author_id, genre_id, title) values (:author_id, :genre_id, :title)", paramMap, keyHolder);
        Long id = keyHolder.getKey().longValue();
        book.setBookId(id);
        return book;
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("book_id", bookId)
                .addValue("author_id", book.getAuthorId())
                .addValue("genre_id", book.getGenreId())
                .addValue("title", book.getTitle());
        jdbcOperations.update("update book set author_id = :author_id, genre_id = :genre_id, title = :title where book_id = :book_id", paramMap);
        book.setBookId(bookId);
        return book;
    }

    @Override
    public void deleteBook(Long bookId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("book_id", bookId);
        jdbcOperations.update("delete from book where book_id = :book_id", paramMap);
    }

    @Override
    public Optional<Book> getById(Long bookId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("book_id", bookId);
        Book book = jdbcOperations.queryForObject("select b.book_id, b.author_id, b.genre_id, b.title, a.lastname, a.firstname, a.middlename, g.name "
                + " from book b"
                + " join author a on a.author_id = b.author_id"
                + " join genre g on g.genre_id = b.genre_id"
                + " where book_id = :book_id", paramMap, new BookRowMapper());
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> getAll() {
        List<Book> bookList = jdbcOperations.query("select b.book_id, b.author_id, b.genre_id, b.title, a.lastname, a.firstname, a.middlename, g.name "
                + " from book b"
                + " join author a on a.author_id = b.author_id"
                + " join genre g on g.genre_id = b.genre_id"
                + " order by b.book_id asc", new BookRowMapper());
        
        return bookList;
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("author_id", authorId);
        List<Book> bookList = jdbcOperations.query("select b.book_id, b.author_id, b.genre_id, b.title, a.lastname, a.firstname, a.middlename, g.name "
                + " from book b"
                + " join author a on a.author_id = b.author_id"
                + " join genre g on g.genre_id = b.genre_id"
                + " where b.author_id = :author_id" 
                + " order by b.book_id asc", paramMap, new BookRowMapper());
        
        return bookList;
    }

    @Override
    public List<Book> getBooksByGenreId(Long genreId) {
        SqlParameterSource paramMap = new MapSqlParameterSource().addValue("genre_id", genreId);
        List<Book> bookList = jdbcOperations.query("select b.book_id, b.author_id, b.genre_id, b.title, a.lastname, a.firstname, a.middlename, g.name "
                + " from book b"
                + " join author a on a.author_id = b.author_id"
                + " join genre g on g.genre_id = b.genre_id"
                + " where b.genre_id = :genre_id" 
                + " order by b.book_id asc", paramMap, new BookRowMapper());
        
        return bookList;
    }
}
