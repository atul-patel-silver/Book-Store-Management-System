package com.book.store.services;

import com.book.store.modal.BookCategory;

import java.util.List;

public interface BookCategoryService {

    public BookCategory add(BookCategory  bookCategory);

    public BookCategory get(int id);


    public List<BookCategory> getAll();

    public void remove(BookCategory bookCategory);
}
