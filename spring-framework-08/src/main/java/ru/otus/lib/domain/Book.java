package ru.otus.lib.domain;

import java.util.List;

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
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    
    @Field("title")
    private String title;
    
    @DBRef
    private Author author;
    
    @DBRef
    private Genre genre;
    
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @DBRef
    private List<BookComment> commentList;
}
