package com.book.store.restcontroller;

import com.book.store.modal.*;
import com.book.store.payload.ApiResponse;
import com.book.store.payload.EmployeePayLoad;
import com.book.store.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {




    @Autowired
    private BookCategoryService  bookCategoryService;

    @Autowired
    private BCryptPasswordEncoder  passwordEncoder;
    private Logger logger= LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private BookService bookService;
    @Autowired
    private EmployeeService  employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;


    //add Category
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody BookCategory bookCategory){
          bookCategory.setCategoryAddDate(LocalDate.now());
        BookCategory bookCategory1 = this.bookCategoryService.add(bookCategory);
        return new ResponseEntity<>(bookCategory1.getCategoryTitle()+" book category  added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<List<BookCategory>> getAllCategory(){
        List<BookCategory> categories = this.bookCategoryService.getAll();
        return new ResponseEntity<>(categories,HttpStatus.FOUND);
    }

    @GetMapping("/get-single-category/{id}")
    public ResponseEntity<BookCategory> getSingleCategory(@PathVariable("id") int id){

        return new ResponseEntity<>(this.bookCategoryService.get(id),HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id){
        BookCategory bookCategory = this.bookCategoryService.get(id);
        this.bookCategoryService.remove(bookCategory);
        return new ResponseEntity<>("Book Category has been Deleted!!",HttpStatus.ACCEPTED);
    }


    //for books

    @PostMapping("/add-book")
    public ResponseEntity<String>  addBook(@RequestBody Book book,@RequestParam("category_id") int id){
        BookCategory bookCategory = this.bookCategoryService.get(id);
        book.setBookAddDate(LocalDate.now());
        book.setCategory(bookCategory);
        Book book2 = this.bookService.add(book);

        return new ResponseEntity<>(book2.getBookTitle()+" is added Successfully",HttpStatus.CREATED);

    }

    @GetMapping("/get-single-book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id){
        Book book = this.bookService.get(id);
        return new ResponseEntity<>(book,HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(this.bookService.getAll(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id){
        Book book = this.bookService.get(id);
        this.bookService.remove(book);

        return new ResponseEntity<>("Book has been Deleted !!",HttpStatus.CREATED);
    }


    @PostMapping("/add-manager")
    public ResponseEntity<?> addManager(@RequestBody EmployeePayLoad  manager){
        try {
            Customer customer1 = this.customerService.customerFindByEmailId(manager.getEmployeeEmailId());
            if (customer1 == null) {

                Employee manager1 = Employee.builder()
                        .employeePhoneNumber(manager.getEmployeePhoneNumber())
                        .employeePosition("MANAGER")
                        .employeeSalary(manager.getEmployeeSalary())
                        .build();

                Customer customer = Customer.builder()
                        .customerEmailId(manager.getEmployeeEmailId())
                        .role("ROLE_EMPLOYEE-M")
                        .employee(manager1)
                        .password(this.passwordEncoder.encode(manager.getEmployeePassword()))
                        .joinDate(LocalDateTime.now())
                        .customerName(manager.getEmployeeName())
                        .enable(false)
                        .build();
                Customer save = this.customerService.save(customer);

                Address build = Address.builder()
                        .area(manager.getArea())
                        .pinCode(manager.getPinCode())
                        .city(manager.getCity())
                        .state(manager.getState())
                        .buildingName(manager.getBuildingName())
                        .houseNo(manager.getHouseNo())
                        .colony(manager.getColony())
                        .district(manager.getDistrict())
                        .customer(save)
                        .build();

                this.addressService.add(build);
                ApiResponse apiResponse = ApiResponse.builder().message("Manager Added Successfully").success(true).build();
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            } else {
                ApiResponse apiResponse = ApiResponse.builder().message("Already Register This Email Id Manager try Another One...").success(false).build();

                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        }catch (Exception e){
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong Try After Some Time...").success(false).build();
            e.printStackTrace();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
    }



    @GetMapping("/delete-manager/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable("id") long id) {
        try {
            Employee employee = this.employeeService.get(id);
            Customer customer = employee.getCustomer();

            // Remove the associations
            employee.setCustomer(null);

            // You don't need to set the customer to null for each address individually,
            // as orphan removal is specified in the entity relationship.

            // Remove the customer (which should cascade to remove associated addresses)
            this.customerService.remove(customer);

            // Remove the employee
            this.employeeService.delete(employee);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong. Try After Some Time...").success(false).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        ApiResponse apiResponse = ApiResponse.builder().message("Successfully banned Manager").success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }





}
