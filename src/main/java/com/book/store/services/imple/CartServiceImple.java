package com.book.store.services.imple;

import com.book.store.exception.ResourceNotFoundException;
import com.book.store.modal.Book;
import com.book.store.modal.Cart;
import com.book.store.modal.Customer;
import com.book.store.repository.CartRepository;
import com.book.store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImple implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Cart add(Cart cart) {
        return this.cartRepository.save(cart);
    }

    @Override
    public Cart get(Long id) {
        return this.cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart"," Id ",Integer.toString(Math.toIntExact(id))));
    }

    @Override
    public List<Cart> findAll() {
        return this.cartRepository.findAll();
    }

    @Override
    public void delete(Cart cart) {
              this.cartRepository.delete(cart);
    }

    @Override
    public int countCart(Customer customer) {
        return this.cartRepository.countCart(customer);
    }

    @Override
    public Cart findCartForBookAndCustomer(Customer customer, Book book) {
        return this.cartRepository.findCartForBookAndCustomer(customer,book);
    }

    @Override
    public List<Cart> findCartForCustomer(Customer customer) {
        return this.cartRepository.findCartForCustomer(customer);
    }
}
