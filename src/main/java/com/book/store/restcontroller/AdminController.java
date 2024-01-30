package com.book.store.restcontroller;

import com.book.store.modal.Book;
import com.book.store.modal.BookCategory;
import com.book.store.services.BookCategoryService;
import com.book.store.services.BookService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {




    @Autowired
    private BookCategoryService  bookCategoryService;

    @Autowired
    private BookService bookService;


    //add Category
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody BookCategory bookCategory){
          bookCategory.setCategoryAddDate(LocalDate.now());
        BookCategory bookCategory1 = this.bookCategoryService.add(bookCategory);
        return new ResponseEntity<>(bookCategory1.getCategoryTitle()+" book category  added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<List<BookCategory>> getAllCategory(){
        List<BookCategory> categories = this.bookCategoryService.getAll();
        return new ResponseEntity<>(categories,HttpStatus.FOUND);
    }

    @GetMapping("/get-single-category/{id}")
    public ResponseEntity<BookCategory> getSingleCategory(@PathVariable("id") int id){

        return new ResponseEntity<>(this.bookCategoryService.get(id),HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id){
        BookCategory bookCategory = this.bookCategoryService.get(id);
        this.bookCategoryService.remove(bookCategory);
        return new ResponseEntity<>("Book Category has been Deleted!!",HttpStatus.ACCEPTED);
    }


    //for books

    @PostMapping("/add-book")
    public ResponseEntity<String>  addBook(@RequestBody Book book,@RequestParam("category_id") int id){
        BookCategory bookCategory = this.bookCategoryService.get(id);
        book.setBookAddDate(LocalDate.now());
        book.setCategory(bookCategory);
        Book book2 = this.bookService.add(book);

        return new ResponseEntity<>(book2.getBookTitle()+" is added Successfully",HttpStatus.CREATED);

    }

    @GetMapping("/get-single-book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id){
        Book book = this.bookService.get(id);
        return new ResponseEntity<>(book,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(this.bookService.getAll(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id){
        Book book = this.bookService.get(id);
        this.bookService.remove(book);

        return new ResponseEntity<>("Book has been Deleted !!",HttpStatus.CREATED);
    }
}
