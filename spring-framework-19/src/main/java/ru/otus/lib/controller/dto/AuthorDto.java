package ru.otus.lib.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.lib.domain.Author;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

    private Long id;
    
    private String lastname;
    
    private String firstname;
    
    private String middlename;

    public static AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .lastname(author.getLastname())
                .firstname(author.getFirstname())
                .middlename(author.getMiddlename())
                .build();
    }

}
