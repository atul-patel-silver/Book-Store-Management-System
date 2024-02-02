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
    private BookCategoryService bookCategoryService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private BookService bookService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;



    //start all category methode
    //add Category
    @PostMapping("/add-category")
    public ResponseEntity<String> addCategory(@RequestBody BookCategory bookCategory) {
        bookCategory.setCategoryAddDate(LocalDate.now());
        BookCategory bookCategory1 = this.bookCategoryService.add(bookCategory);
        return new ResponseEntity<>(bookCategory1.getCategoryTitle() + " book category  added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/get-all-category")
    public ResponseEntity<List<BookCategory>> getAllCategory() {
        List<BookCategory> categories = this.bookCategoryService.getAll();
        return new ResponseEntity<>(categories, HttpStatus.FOUND);
    }

    @GetMapping("/get-single-category/{id}")
    public ResponseEntity<BookCategory> getSingleCategory(@PathVariable("id") int id) {

        return new ResponseEntity<>(this.bookCategoryService.get(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        BookCategory bookCategory = this.bookCategoryService.get(id);
        this.bookCategoryService.remove(bookCategory);
        return new ResponseEntity<>("Book Category has been Deleted!!", HttpStatus.ACCEPTED);
    }

//end all category methode

    //for books
    @PostMapping("/add-book")
    public ResponseEntity<String> addBook(@RequestBody Book book, @RequestParam("category_id") int id) {
        BookCategory bookCategory = this.bookCategoryService.get(id);
        book.setBookAddDate(LocalDate.now());
        book.setCategory(bookCategory);
        Book book2 = this.bookService.add(book);

        return new ResponseEntity<>(book2.getBookTitle() + " is added Successfully", HttpStatus.CREATED);

    }

    @GetMapping("/get-single-book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id) {
        Book book = this.bookService.get(id);
        return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(this.bookService.getAll(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") int id) {
        Book book = this.bookService.get(id);
        this.bookService.remove(book);

        return new ResponseEntity<>("Book has been Deleted !!", HttpStatus.CREATED);
    }



    //manager data
    @PostMapping("/add-manager")
    public ResponseEntity<?> addManager(@RequestBody EmployeePayLoad manager) {
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
                        .password(this.passwordEncoder.encode(manager.getEmployeePassword()))
                        .joinDate(LocalDateTime.now())
                        .customerName(manager.getEmployeeName())
                        .enable(false)
                        .build();
                Customer save1 = this.customerService.save(customer);
                Address build = Address.builder()
                        .area(manager.getArea())
                        .pinCode(manager.getPinCode())
                        .city(manager.getCity())
                        .state(manager.getState())
                        .buildingName(manager.getBuildingName())
                        .houseNo(manager.getHouseNo())
                        .colony(manager.getColony())
                        .district(manager.getDistrict())
                        .build();

                build.setCustomer(save1);
                this.addressService.add(build);

                manager1.setCustomer(save1);
                this.employeeService.add(manager1);


                ApiResponse apiResponse = ApiResponse.builder().message("Manager Added Successfully").success(true).build();
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            } else {
                ApiResponse apiResponse = ApiResponse.builder().message("Already Register This Email Id Manager try Another One...").success(false).build();

                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong Try After Some Time...").success(false).build();
            e.printStackTrace();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
    }


    @GetMapping("/delete-manager/{id}")
    public ResponseEntity<?> deleteManager(@PathVariable("id") long id) {
        try {
            System.out.println(id);
            Employee employee = this.employeeService.get(id);
            Customer customer = employee.getCustomer();
            List<Address> address = customer.getAddress();
            for (Address a : address) {
                this.addressService.delete(a);
            }
            this.customerService.remove(customer);
            employee.setCustomer(null);

            this.employeeService.delete(employee);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong. Try After Some Time...").success(false).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        ApiResponse apiResponse = ApiResponse.builder().message("Successfully banned Manager").success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/all-manager")
    public ResponseEntity<?> getAllManager() {
        List<Employee> employees = this.employeeService.allManager();
       for (Employee e: employees){
           System.out.println(e.getCustomer().getCustomerName());
       }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


    //staff
    @PostMapping("/add-staff")
    public ResponseEntity<?> addStaff(@RequestBody EmployeePayLoad staff) {
        try {
            Customer customer1 = this.customerService.customerFindByEmailId(staff.getEmployeeEmailId());
            if (customer1 == null) {

                Employee manager1 = Employee.builder()
                        .employeePhoneNumber(staff.getEmployeePhoneNumber())
                        .employeePosition("STAFF")
                        .employeeSalary(staff.getEmployeeSalary())
                        .build();

                Customer customer = Customer.builder()
                        .customerEmailId(staff.getEmployeeEmailId())
                        .role("ROLE_EMPLOYEE-S")
                        .password(this.passwordEncoder.encode(staff.getEmployeePassword()))
                        .joinDate(LocalDateTime.now())
                        .customerName(staff.getEmployeeName())
                        .enable(false)
                        .build();
                Customer save1 = this.customerService.save(customer);
                Address build = Address.builder()
                        .area(staff.getArea())
                        .pinCode(staff.getPinCode())
                        .city(staff.getCity())
                        .state(staff.getState())
                        .buildingName(staff.getBuildingName())
                        .houseNo(staff.getHouseNo())
                        .colony(staff.getColony())
                        .district(staff.getDistrict())
                        .build();

                build.setCustomer(save1);
                this.addressService.add(build);

                manager1.setCustomer(save1);
                this.employeeService.add(manager1);


                ApiResponse apiResponse = ApiResponse.builder().message("Staff Added Successfully").success(true).build();
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            } else {
                ApiResponse apiResponse = ApiResponse.builder().message("Already Register This Email Id Staff try Another One...").success(false).build();

                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong Try After Some Time...").success(false).build();
            e.printStackTrace();
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        }
    }



    @GetMapping("/delete-staff/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable("id") long id) {
        try {
            System.out.println(id);
            Employee employee = this.employeeService.get(id);
            Customer customer = employee.getCustomer();
            List<Address> address = customer.getAddress();
            for (Address a : address) {
                this.addressService.delete(a);
            }
            this.customerService.remove(customer);
            employee.setCustomer(null);

            this.employeeService.delete(employee);
        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse apiResponse = ApiResponse.builder().message("Something Went Wrong. Try After Some Time...").success(false).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        ApiResponse apiResponse = ApiResponse.builder().message("Successfully banned Staff").success(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/all-staff")
    public ResponseEntity<?> getAllStaff() {
        List<Employee> employees = this.employeeService.allStaff();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


}
