package com.book.store.payload;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class EmployeePayLoad {
    private String employeeName;
    private String employeeEmailId;
    private String employeePassword;
    private String employeePhoneNumber;
    private String employeeSalary;
    private String pinCode;
    private String state;
    private String district;
    private String city;
    private String houseNo;
    private String buildingName;
    private String area;
    private String colony;
}
