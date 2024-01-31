package com.book.store.restcontroller;

import com.book.store.configration.jwtconfigration.JwtHelper;
import com.book.store.modal.Customer;
import com.book.store.payload.ApiResponse;
import com.book.store.payload.JwtRequest;
import com.book.store.payload.JwtResponse;
import com.book.store.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping
public class HomeController {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

@Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private CustomerService  customerService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerCustomer(@RequestBody Customer customer){

        try{

            Customer customer1 = this.customerService.customerFindByEmailId(customer.getCustomerEmailId());
            if(customer1 == null) {
                customer.setRole("ROLE_CUSTOMER");
                customer.setJoinDate(LocalDateTime.now());
                customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
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



    //login


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
