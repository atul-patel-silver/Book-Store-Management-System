package com.book.store.repository;

import com.book.store.modal.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query("SELECT book from Book book WHERE book.bookTitle LIKE %:query% OR book.bookAuthor LIKE %:query% OR book.bookISBN LIKE %:query% OR book.category.categoryTitle LIKE %:query%")
    public List<Book> searchBook(@Param("query") String query);
    @Query("select book from Book as book where book.category.categoryTitle =:categoryTitle")
    public List<Book> findByBookCategoryTitle(@Param("categoryTitle") String categoryTitle);

}
