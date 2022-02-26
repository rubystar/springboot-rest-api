package com.example.employeemanagement.dtos;

import com.example.employeemanagement.entities.Employee;
import lombok.Getter;

@Getter
public class EmployeeDto {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;

  public EmployeeDto(Employee employee) {
    this.id = employee.getId();
    this.firstName = employee.getFirstName();
    this.lastName = employee.getLastName();
    this.email = employee.getEmail();
    this.phoneNumber = employee.getPhoneNumber();
  }
}
