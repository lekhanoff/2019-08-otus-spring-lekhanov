package ru.otus.lib.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Book {

    private Long bookId;
    
    private String title;
    
    private Long authorId;
    
    private Author author;
    
    private Long genreId;
    
    private Genre genre;
}
