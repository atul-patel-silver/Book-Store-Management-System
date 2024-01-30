package com.book.store.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tbl_Book_Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookCategory_Id")
    private Long categoryId;

    @Column(name = "bookCategory_Title")
    private String categoryTitle;

    @Column(name = "bookCategory_Discription",length = 6000)
    private String categoryDiscription;

    @Column(name = "bookCategory_AddDate")
    private LocalDate categoryAddDate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Book> books;
}
