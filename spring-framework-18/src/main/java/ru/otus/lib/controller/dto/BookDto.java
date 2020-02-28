package ru.otus.lib.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.lib.domain.Book;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private AuthorDto author;

    @JsonProperty("genre")
    private GenreDto genre;

    public static BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(AuthorDto.toDto(book.getAuthor()))
                .genre(GenreDto.toDto(book.getGenre()))
                .build();
    }
}
