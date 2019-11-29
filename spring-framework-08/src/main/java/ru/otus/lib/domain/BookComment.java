package ru.otus.lib.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class BookComment {

    @Id
    @Field
    private String id;
    
    @DBRef
    private Book book;
    
    @Field("userLogin")
    private String userLogin;
    
    @Field("userComment")
    private String userComment;
}
