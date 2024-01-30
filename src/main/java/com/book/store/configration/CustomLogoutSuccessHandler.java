package com.book.store.configration;


import com.book.store.modal.Customer;
import com.book.store.modal.LoggedInCustomer;
import com.book.store.services.CustomerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private LoggedInCustomer loggedInCustomer;
    @Autowired
    private CustomerService customerService;


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication != null) {
            String username = authentication.getName();
            Customer customer = this.customerService.customerFindByEmailId(username);
            customer.setEnable(false);
            this.customerService.save(customer);
            this.loggedInCustomer.removeUser(username);
        }
//        response.sendRedirect("/Book-Store/signin");
    }
}