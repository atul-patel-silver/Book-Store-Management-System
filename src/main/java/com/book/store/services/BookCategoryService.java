package com.book.store.services;

import com.book.store.modal.BookCategory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookCategoryService {

    public BookCategory add(BookCategory  bookCategory);

    public BookCategory get(int id);


    public List<BookCategory> getAll();

    public Page<BookCategory> findByPagination(Pageable pageable);
    List<BookCategory> searchByTitle(String title);
    public void remove(BookCategory bookCategory);
}
