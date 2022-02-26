package com.example.employeemanagement.services;

import com.example.employeemanagement.dtos.EmployeeDto;
import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.exceptions.RecordNotFoundException;
import com.example.employeemanagement.repositories.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
  @Autowired private EmployeeRepository employeeRepository;

  public ResponseEntity<Employee> saveEmployee(Employee employee) {
    Employee savedEmployee = employeeRepository.save(employee);
    return new ResponseEntity<Employee>(savedEmployee, HttpStatus.CREATED);
  }

  public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
    List employees = employeeRepository.findAll();

    List<EmployeeDto> allEmployees =
        (List<EmployeeDto>)
            employees.stream()
                .map(
                    objA -> {
                      return new EmployeeDto((Employee) objA);
                    })
                .collect(Collectors.toList());

    return new ResponseEntity<List<EmployeeDto>>(allEmployees, HttpStatus.OK);
  }

  public ResponseEntity<Employee> getEmployeeById(Long id) {

    Employee employee = findExistingEmployeeById(id);
    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
  }

  public ResponseEntity<Employee> updateEmployee(Long id, Employee apiEmployee) {
    Employee employee = findExistingEmployeeById(id);
    // Todo: inefficient, use an update db query
    if (employee != null) {
      employee.setFirstName(apiEmployee.getFirstName());
      employee.setLastName(apiEmployee.getLastName());
      employee.setSalary(apiEmployee.getSalary());
      employee.setPhoneNumber(apiEmployee.getPhoneNumber());
    }
    employeeRepository.save(employee);
    return new ResponseEntity<Employee>(employee, HttpStatus.OK);
  }

  public ResponseEntity<Employee> deleteEmployee(Long id) {
    Employee employee = findExistingEmployeeById(id);
    employeeRepository.deleteById(employee.getId());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  private Employee findExistingEmployeeById(Long id) {
    Optional<Employee> employee = employeeRepository.findById(id);

    if (employee.equals(Optional.empty())) {
      throw new RecordNotFoundException();
    }
    return employee.get();
  }
}
