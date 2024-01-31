package com.book.store.repository;

import com.book.store.modal.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCategoryRepository extends JpaRepository<BookCategory,Integer> {

    @Query("select b from BookCategory as b where b.categoryTitle LIKE %:title%")
    public List<BookCategory> searchByTitle(@Param("title") String title);
}
