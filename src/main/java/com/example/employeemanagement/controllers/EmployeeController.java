package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.EmployeeDto;
import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.services.EmployeeService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @PostMapping
  public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
    return employeeService.saveEmployee(employee);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable Long id, @Valid @RequestBody Employee employee) {
    return employeeService.updateEmployee(id, employee);
  }

  @DeleteMapping("/{id}")
  public void deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
  }
}
