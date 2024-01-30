package com.book.store.repository;

import com.book.store.modal.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryRepository extends JpaRepository<BookCategory,Integer> {
}
