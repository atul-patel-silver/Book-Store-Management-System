package com.book.store.restcontroller;

import com.book.store.modal.Customer;
import com.book.store.payload.ApiResponse;
import com.book.store.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/home")
public class HomeController {



    private CustomerService  customerService;
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody Customer customer){

        try{

            Customer customer1 = this.customerService.customerFindByEmailId(customer.getCustomerEmailId());
            if(customer1 == null) {
                customer.setRole("ROLE_CUSTOMER");
                customer.setJoinDate(LocalDateTime.now());
                this.customerService.save(customer);
                ApiResponse apiResponse = ApiResponse.builder().message("Customer Added Successfully").success(true).build();
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
            else {
                ApiResponse apiResponse = ApiResponse.builder().message("Already Register This Email Id User Another One...").success(false).build();

                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        }
        catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong Try After Some Time...").success(false).build();
            e.printStackTrace();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }

    }
}
