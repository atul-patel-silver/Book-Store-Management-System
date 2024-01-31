package com.book.store.services;

import com.book.store.modal.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {

    public Book add(Book book);

    public Book get(int id);


    public List<Book> getAll();

    public void remove(Book book);
    public List<Book> findByBookCategoryTitle(String categoryTitle);

    public List<Book> searchBook(String query);

    public Page<Book> getBookByPagination(Pageable pageable);
}
