package com.book.store.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_Id")
    private Long addressId;

    @Column(name = "address_PersonName")
    private String fullName;

    @Column(name = "address_PhoneNumber")
    private String phoneNumber;
    @Column(name = "address_Pincode")
    private String pinCode;
    @Column(name = "address-State")
    private String state;
    @Column(name = "address_district")
    private String district;
    @Column(name = "address_City")
    private String city;

    @Column(name = "address_HouseNo")
    private String houseNo;
    @Column(name = "address_BuildingName")
    private String buildingName;

    @Column(name = "address_Ares")
    private String area;

    @Column(name = "address_Colony")
    private String colony;

    @ManyToOne
    @JoinColumn(name = "customer_Id")
    private Customer customer;
}
