package ru.otus.lib.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.otus.lib.controller.dto.AuthorDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    @Id
    @Column(name = "author_id")
    @GeneratedValue(generator = "author_author_id_seq")
    @SequenceGenerator(name = "author_author_id_seq", sequenceName = "author_author_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "middlename")
    private String middlename;

    @JsonBackReference("author")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> books;

    public static Author fromDto(AuthorDto author) {
        return Author.builder()
                .id(author.getId())
                .lastname(author.getLastname())
                .firstname(author.getFirstname())
                .middlename(author.getMiddlename())
                .build();
    }
}
