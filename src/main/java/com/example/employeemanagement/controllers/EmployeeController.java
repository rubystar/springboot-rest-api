package com.example.employeemanagement.controllers;

import com.example.employeemanagement.dtos.EmployeeDto;
import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Employees", description = "Endpoints for managing employees")
@RequestMapping("/employees")
public class EmployeeController {

  @Autowired private EmployeeService employeeService;

  // Todo: Customise api response for swagger
  @Operation(summary = "Get all Employees")
  @GetMapping
  public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @Operation(summary = "Create an employee")
  @PostMapping
  public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody Employee employee) {
    return employeeService.saveEmployee(employee);
  }

  @Operation(summary = "Update an employee")
  @PutMapping(value = "/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable Long id, @Valid @RequestBody Employee employee) {
    return employeeService.updateEmployee(id, employee);
  }

  @Operation(summary = "Get an employee")
  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployee(
      @Parameter(name = "employeeId", required = true) @PathVariable Long id) {
    return employeeService.getEmployeeById(id);
  }

  @Operation(summary = "Delete an employee")
  @DeleteMapping("/{id}")
  public void deleteEmployee(
      @Parameter(name = "employeeId", required = true) @PathVariable Long id) {
    employeeService.deleteEmployee(id);
  }
}
