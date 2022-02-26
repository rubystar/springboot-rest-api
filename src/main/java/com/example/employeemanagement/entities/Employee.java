package com.example.employeemanagement.entities;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Employee")
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false)
  private Long id;

  @NotEmpty(message = "first name cannot be blank")
  @Size(min = 6, message = "first name should have at least 6 characters")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotEmpty
  @Email
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  private Integer salary;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "created_at")
  private Date createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = updatedAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

  public Employee(
      String firstName, String lastName, String email, Integer salary, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.salary = salary;
    this.phoneNumber = phoneNumber;
  }
}
