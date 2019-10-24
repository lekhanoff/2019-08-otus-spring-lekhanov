package ru.otus.lib.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Author {

    private Long authorId;
    
    private String lastname;
    
    private String firstname;
    
    private String middlename;
}
