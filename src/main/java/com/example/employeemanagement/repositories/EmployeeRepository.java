package com.example.employeemanagement.repositories;

import com.example.employeemanagement.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
