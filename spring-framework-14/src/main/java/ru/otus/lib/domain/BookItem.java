package ru.otus.lib.domain;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "books")
public class BookItem {

    @Id
    private String id;
    
    @Column(name = "title")
    private String title;
    
    @JsonManagedReference("author")
    @DBRef
    private Author author;
    
    @JsonManagedReference("genre")
    @DBRef
    private Genre genre;
}
