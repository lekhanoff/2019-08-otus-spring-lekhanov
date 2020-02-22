package ru.otus.lib.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.lib.domain.Genre;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

    private Long id;
    private String name;

    public static GenreDto toDto(Genre genre) {
        return GenreDto.builder().id(genre.getId()).name(genre.getName()).build();
    }
}
