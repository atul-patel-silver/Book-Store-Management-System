package com.book.store.services.imple;


import com.book.store.exception.ResourceNotFoundException;
import com.book.store.modal.Address;
import com.book.store.repository.AddressRepository;
import com.book.store.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImple implements AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public Address add(Address address) {
        return this.addressRepository.save(address);
    }

    @Override
    public Address get(Long id) {
        return this.addressRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new ResourceNotFoundException("Address"," Id ",Integer.toString(Math.toIntExact(id))));
    }

    @Override
    public List<Address> findAll() {
        return this.addressRepository.findAll();
    }

    @Override
    public void delete(Address address) {
     this.addressRepository.delete(address);
    }

    @Override
    public List<Address> byCustomerId(Long cId) {
        return this.addressRepository.findByCustomerId(cId);
    }
}
