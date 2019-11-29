package ru.otus.lib.shell;

import java.io.IOException;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.lib.domain.BookComment;
import ru.otus.lib.service.BookCommentService;

@ShellComponent
public class BookCommentShell {

    private final BookCommentService bookCommentService;
    
    private final ObjectMapper objectMapper;

    public BookCommentShell(BookCommentService bookCommentService, ObjectMapper objectMapper) {
        this.bookCommentService = bookCommentService;
        this.objectMapper = objectMapper;
    }

    @ShellMethod(value = "Book comment creation", key = {"book-comment-create", "bcc"})
    public String createBookComment() throws IOException {
        BookComment bookComment = bookCommentService.createBookComment();
        return objectMapper.writeValueAsString(bookComment);
    }

    @ShellMethod(value = "Book comment modification", key = {"book-comment-update", "bcu"})
    public String updateBookComment() throws IOException {
        BookComment bookComment = bookCommentService.updateBookComment();
        return objectMapper.writeValueAsString(bookComment);
    }

    @ShellMethod(value = "Find book comment by id", key = {"book-comment-find-id", "bcfi"})
    public String bookCommentFindById() throws IOException {
        return objectMapper.writeValueAsString(bookCommentService.getById().orElse(null));
    }
    
    @ShellMethod(value = "Find book comment by book id", key = {"book-comment-find-book-id", "bcfbi"})
    public String bookCommentFindByBookId() throws IOException {
        return objectMapper.writeValueAsString(bookCommentService.getByBookId());
    }
    
    @ShellMethod(value = "Book comment deletion", key = {"book-comment-delete", "bcd"})
    public void deleteBookComment() throws IOException {
        bookCommentService.deleteBookComment();
    }

}
