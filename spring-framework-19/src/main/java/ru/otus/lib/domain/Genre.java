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
import ru.otus.lib.controller.dto.GenreDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(generator = "genre_genre_id_seq")
    @SequenceGenerator(name = "genre_genre_id_seq", sequenceName = "genre_genre_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonBackReference("genre")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "genre", fetch = FetchType.EAGER)
    private Set<Book> books;

    public static Genre fromDto(GenreDto genre) {
        return Genre.builder().id(genre.getId()).name(genre.getName()).build();
    }
}
