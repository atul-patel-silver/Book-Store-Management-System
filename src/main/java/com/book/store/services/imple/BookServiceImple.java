package com.book.store.services.imple;

import com.book.store.exception.ResourceNotFoundException;
import com.book.store.modal.Book;
import com.book.store.repository.BookRepository;
import com.book.store.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImple implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book add(Book book) {
        return this.bookRepository.save(book);
    }

    @Override
    public Book get(int id) {
        return this.bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book"," Id ",Integer.toString(id)));
    }

    @Override
    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public void remove(Book book) {
        this.bookRepository.delete(book);
    }
}
