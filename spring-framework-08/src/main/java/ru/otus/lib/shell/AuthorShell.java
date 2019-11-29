package ru.otus.lib.shell;

import java.io.IOException;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.lib.domain.Author;
import ru.otus.lib.service.AuthorService;

@ShellComponent
public class AuthorShell {

    private final AuthorService authorService;
    
    private final ObjectMapper objectMapper;

    public AuthorShell(AuthorService authorService, ObjectMapper objectMapper) {
        this.authorService = authorService;
        this.objectMapper = objectMapper;
    }

    @ShellMethod(value = "Author creation", key = {"author-create", "ac"})
    public String createAuthor() throws IOException {
        Author author = authorService.createAuthor();
        return objectMapper.writeValueAsString(author);
    }

    @ShellMethod(value = "Author modification", key = {"author-update", "au"})
    public String updateAuthor() throws IOException {
        Author author = authorService.updateAuthor();
        return objectMapper.writeValueAsString(author);
    }

    @ShellMethod(value = "Find author by id", key = {"author-find-id", "afi"})
    public String authorFindById() throws IOException {
        return objectMapper.writeValueAsString(authorService.getById().orElse(null));
    }
    
    @ShellMethod(value = "Find all authors", key = {"author-find-all", "afa"})
    public String authorFindAll() throws IOException {
        List<Author> authorList = authorService.getAll();
        return objectMapper.writeValueAsString(authorList);
    }

    @ShellMethod(value = "Author deletion", key = {"author-delete", "ad"})
    public void deleteAuthor() throws IOException {
        authorService.deleteAuthor();
    }
}
