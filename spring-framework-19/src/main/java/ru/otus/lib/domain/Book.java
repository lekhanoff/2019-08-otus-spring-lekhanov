package ru.otus.lib.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.lib.controller.dto.BookDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(generator = "book_book_id_seq")
    @SequenceGenerator(name = "book_book_id_seq", sequenceName = "book_book_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title")
    private String title;

    @JsonManagedReference("author")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @JsonManagedReference("genre")
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
    
    @JsonBackReference("comment")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookComment> comments;

    @JsonBackReference("rating")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookRating> ratings;

    @JsonBackReference("compilationitem")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookCompilationItem> compilationItems;

    
    public static Book fromDto(BookDto book) {
        return Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(Author.fromDto(book.getAuthor()))
                .genre(Genre.fromDto(book.getGenre()))
                .build();
    }
}
