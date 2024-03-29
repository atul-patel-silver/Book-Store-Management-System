package com.book.store.configration;

import com.book.store.modal.Customer;
import com.book.store.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailServiceImple implements UserDetailsService {

    @Autowired
    private CustomerService customerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer =this.customerService.customerFindByEmailId(username);
        if(customer== null) {
            throw new UsernameNotFoundException("could Not Found !!! ");
        }
        CustomCustomerDetail customCustomerDetail = new CustomCustomerDetail(customer);
        return customCustomerDetail;
    }


}