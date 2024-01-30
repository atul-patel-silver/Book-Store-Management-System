package com.book.store.services;

import com.book.store.modal.Book;

import java.util.List;

public interface BookService {

    public Book add(Book book);

    public Book get(int id);


    public List<Book> getAll();

    public void remove(Book book);
}
