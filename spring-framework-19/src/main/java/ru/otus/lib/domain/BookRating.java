package ru.otus.lib.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_rating")
public class BookRating {

    @Id
    @GeneratedValue(generator = "book_rating_book_rating_id_seq")
    @SequenceGenerator(name = "book_rating_book_rating_id_seq", sequenceName = "book_rating_book_rating_id_seq", allocationSize = 1)
    @Column(name = "book_rating_id")
    private Long id;
    
    @Column(name = "book_id")
    private Long bookId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "rating")
    private Integer comment;
}
