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
@Table(name = "book_compilation_item")
public class BookCompilationItem {

    @Id
    @GeneratedValue(generator = "book_compilation_item_id_seq")
    @SequenceGenerator(name = "book_compilation_item_id_seq", sequenceName = "book_compilation_item_id_seq", allocationSize = 1)
    @Column(name = "book_compilation_item_id")
    private Long id;

    @Column(name = "book_compilation_id")
    private Long compilationId;

    @Column(name = "book_id")
    private Long bookId;
}
