package com.book.store.repository;


import com.book.store.modal.Book;
import com.book.store.modal.Cart;
import com.book.store.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT COUNT(c) FROM Cart as c WHERE c.customer =:customer")
    public int countCart(@Param("customer") Customer customer);

    @Query("SELECT c FROM Cart c WHERE c.customer = :customer AND c.book = :book")
    public Cart findCartForBookAndCustomer(@Param("customer") Customer customer, @Param("book") Book book);


    @Query("SELECT c FROM Cart as c WHERE c.customer =:customer")
    public List<Cart> findCartForCustomer(@Param("customer")Customer customer);
}
