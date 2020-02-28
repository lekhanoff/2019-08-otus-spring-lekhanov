package ru.otus.lib.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_comment")
public class BookComment {

    @Id
    @GeneratedValue(generator = "book_comment_book_comment_id_seq")
    @SequenceGenerator(name = "book_comment_book_comment_id_seq", sequenceName = "book_comment_book_comment_id_seq", allocationSize = 1)
    @Column(name = "book_comment_id")
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "user_comment")
    private String comment;
    
    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @JsonManagedReference("comment")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
