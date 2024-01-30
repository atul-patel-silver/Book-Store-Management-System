package com.book.store.modal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy =   GenerationType.IDENTITY)
    @Column(name = "customer_Id")
    private int customerId;

    @Column(name = "customerName")
    private String customerName;

    @Column(name = "customer_emailId",unique = true)
    private String customerEmailId;

    private String password;

    @Column(name = "customer_Enable")
    private boolean enable;
    private String role;

    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Address> address;
}
