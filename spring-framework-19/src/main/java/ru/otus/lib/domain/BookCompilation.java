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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_compilation")
public class BookCompilation {

    @Id
    @GeneratedValue(generator = "book_compilation_book_compilation_id_seq")
    @SequenceGenerator(name = "book_compilation_book_compilation_id_seq", sequenceName = "book_compilation_book_compilation_id_seq", allocationSize = 1)
    @Column(name = "book_compilation_id")
    private Long id;
    
    @Column(name = "compilation_name")
    private String compilationName;
    
    @JsonBackReference("compilation")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "bookCompilation", fetch = FetchType.EAGER)
    private Set<BookCompilationItem> compilationItems;

}
