package com.book.store.services;


import com.book.store.modal.Book;
import com.book.store.modal.Cart;
import com.book.store.modal.Customer;

import java.util.List;

public interface CartService {

    public Cart add(Cart cart);

    public Cart get(Long id);

    public List<Cart> findAll();


    public void delete(Cart cart);


    public int countCart(Customer customer);


    public Cart findCartForBookAndCustomer(Customer customer, Book book);


    public List<Cart> findCartForCustomer(Customer customer);
}
