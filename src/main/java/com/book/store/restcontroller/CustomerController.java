package com.book.store.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase-book")
public class CustomerController {

    @GetMapping("/")
    public String test(){
        return "test";
    }

}
