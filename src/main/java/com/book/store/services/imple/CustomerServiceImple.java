package com.book.store.services.imple;

import com.book.store.exception.ResourceNotFoundException;
import com.book.store.modal.Customer;
import com.book.store.repository.CustomerRepository;
import com.book.store.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImple implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer customerFindByEmailId(String customerEmailId) {
        return this.customerRepository.findByCustomerEmailId(customerEmailId);
    }

    @Override
    public Customer get(int id) {
        return this.customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer"," Id ",Integer.toString(id)));
    }

    @Override
    public List<Customer> getAll() {
        return this.customerRepository.findAll();
    }

    @Override
    public void remove(Customer customer) {
        this.customerRepository.delete(customer);
    }
}
