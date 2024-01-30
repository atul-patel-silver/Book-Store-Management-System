package com.book.store.repository;

import com.book.store.modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    @Query("select c from Customer c where c.customerEmailId =:customerEmailId")
    Customer findByCustomerEmailId(@Param("customerEmailId") String customerEmailId);
}
