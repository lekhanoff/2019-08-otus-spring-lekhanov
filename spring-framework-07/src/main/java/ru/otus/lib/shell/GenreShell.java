package ru.otus.lib.shell;

import java.io.IOException;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.otus.lib.domain.Genre;
import ru.otus.lib.service.GenreService;

@ShellComponent
public class GenreShell {

    private final GenreService genreService;
    
    private final ObjectMapper objectMapper;

    public GenreShell(GenreService genreService, ObjectMapper objectMapper) {
        this.genreService = genreService;
        this.objectMapper = objectMapper;
    }

    @ShellMethod(value = "Genre creation", key = {"genre-create", "gc"})
    public String createGenre() throws IOException {
        Genre genre = genreService.createGenre();
        return objectMapper.writeValueAsString(genre);
    }

    @ShellMethod(value = "Genre modification", key = {"genre-update", "gu"})
    public String updateGenre() throws IOException {
        Genre genre = genreService.updateGenre();
        return objectMapper.writeValueAsString(genre);
    }

    @ShellMethod(value = "Find genre by id", key = {"genre-find-id", "gfi"})
    public String genreFindById() throws IOException {
        return objectMapper.writeValueAsString(genreService.getById().orElse(null));
    }
    
    @ShellMethod(value = "Find genre by name", key = {"genre-find-name", "gfn"})
    public String genreFindByName() throws IOException {
        return objectMapper.writeValueAsString(genreService.getByName().orElse(null));
    }
    
    @ShellMethod(value = "Find all genres", key = {"genre-find-all", "gfa"})
    public String genreFindAll() throws IOException {
        List<Genre> genreList = genreService.getAll();
        return objectMapper.writeValueAsString(genreList);
    }

    @ShellMethod(value = "Genre deletion", key = {"genre-delete", "gd"})
    public void deleteGenre() throws IOException {
        genreService.deleteGenre();
    }
}
