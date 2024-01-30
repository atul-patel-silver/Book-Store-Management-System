package com.book.store.services;

import com.book.store.modal.Customer;

import java.util.List;

public interface CustomerService {


    public Customer save(Customer  customer);


    public Customer customerFindByEmailId(String customerEmailId);


    public Customer get(int id);

    public List<Customer> getAll();

    public void remove(Customer customer);
}
