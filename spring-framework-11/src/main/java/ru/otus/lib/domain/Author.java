package ru.otus.lib.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    private String id;
    
    @Field("lastname")
    private String lastname;
    
    @Field("firstname")
    private String firstname;
    
    @Field("middlename")
    private String middlename;
    
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @DBRef
    private Set<Book> books;
    
//    public static Author fromDto(AuthorDto author) {
//        return Author.builder()
//                .id(author.getId())
//                .lastname(author.getLastname())
//                .firstname(author.getFirstname())
//                .middlename(author.getMiddlename())
//                .build();
//    }
}
