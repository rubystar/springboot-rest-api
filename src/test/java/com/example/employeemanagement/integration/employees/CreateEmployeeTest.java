package com.example.employeemanagement.integration.employees;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.employeemanagement.controllers.EmployeeController;
import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.services.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(EmployeeController.class)
public class CreateEmployeeTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private EmployeeRepository employeeRepository;
  @MockBean private EmployeeService employeeService;

  @Test
  public void createNewEmployee() throws Exception {
    when(employeeService.saveEmployee(getTestEmployee()))
        .thenReturn(new ResponseEntity<Employee>(getTestEmployee(), HttpStatus.OK));
    String expectedResult = getTestEmployeeJsonString();

    MvcResult result =
        mockMvc
            .perform(
                post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getNewEmployeeJsonString()))
            .andReturn();
    assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  @Test
  @DisplayName("Returns validation eorrors when creating an employee with invalid data")
  public void createAnEmployeeWithInvalidData() throws Exception {
    String expectedResult = getInvalidResponse();

    MvcResult result =
        mockMvc
            .perform(
                post("/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getInvalidTestEmployeeString()))
            .andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  // Todo: probably use gson and pojos to clean this up
  private Employee getTestEmployee() {
    return new Employee("Dan", "Abramov", "dan@gmail.com", 10000, "9959345033");
  }

  private String getInvalidTestEmployeeString() {
    return "{\"firstName\":\"D\",\"lastName\":\"Abramov\",\"email\":\"dancom\",\"salary\":10000,\"phoneNumber\":\"9959345033\"}";
  }

  private String getNewEmployeeJsonString() {
    return "{\"firstName\":\"Dan\",\"lastName\":\"Abramov\",\"email\":\"dan@gmail.com\",\"salary\":10000,\"phoneNumber\":\"9959345033\"}";
  }

  private String getTestEmployeeJsonString() {
    return "{\"id\":null,\"firstName\":\"Dan\",\"lastName\":\"Abramov\",\"email\":\"dan@gmail.com\",\"salary\":10000,\"phoneNumber\":\"9959345033\",\"updatedAt\":null,\"createdAt\":null}";
  }

  private String getInvalidResponse() {
    return "{\"firstName\":\"first name should have at least 3 characters\",\"email\":\"must be a well-formed email address\"}";
  }
}
