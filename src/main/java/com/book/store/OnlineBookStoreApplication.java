package com.book.store;

import com.book.store.modal.Customer;
import com.book.store.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class OnlineBookStoreApplication  implements CommandLineRunner {

	@Autowired
	private CustomerService  customerService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = Customer.builder().customerName("Atul Patel").customerEmailId("ajpatel7096@gmail.com").password(this.bCryptPasswordEncoder.encode("atul@2001")).role("ROLE_ADMIN").enable(false).joinDate(LocalDateTime.now()).build();

		Customer customer1 = this.customerService.customerFindByEmailId(customer.getCustomerEmailId());
		if(customer1 == null){
			this.customerService.save(customer);
		}
		else {

		}
	}
}
