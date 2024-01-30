package com.book.store.services.imple;

import com.book.store.exception.ResourceNotFoundException;
import com.book.store.modal.BookCategory;
import com.book.store.repository.BookCategoryRepository;
import com.book.store.services.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookCategoryServiceImple implements BookCategoryService {

    @Autowired
    private BookCategoryRepository  bookCategoryRepository;

    @Override
    public BookCategory add(BookCategory bookCategory) {
        return this.bookCategoryRepository.save(bookCategory);
    }

    @Override
    public BookCategory get(int id) {
        return this.bookCategoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Book Category"," Id ",Integer.toString(id)));
    }

    @Override
    public List<BookCategory> getAll() {
        return this.bookCategoryRepository.findAll();
    }

    @Override
    public void remove(BookCategory bookCategory) {
        this.bookCategoryRepository.delete(bookCategory);
    }
}
