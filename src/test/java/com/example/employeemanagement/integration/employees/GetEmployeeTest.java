package com.example.employeemanagement.integration.employees;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.employeemanagement.controllers.EmployeeController;
import com.example.employeemanagement.entities.Employee;
import com.example.employeemanagement.exceptions.RecordNotFoundException;
import com.example.employeemanagement.repositories.EmployeeRepository;
import com.example.employeemanagement.services.EmployeeService;
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
public class GetEmployeeTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private EmployeeRepository employeeRepository;
  @MockBean private EmployeeService employeeService;

  @Test
  public void GetAnEmployeeByIdWhenExists() throws Exception {
    when(employeeService.getEmployeeById(1L))
        .thenReturn(new ResponseEntity<Employee>(getTestEmployee(), HttpStatus.OK));
    String expectedResult = getTestEmployeeJsonString();

    MvcResult result =
        mockMvc
            .perform(get("/employees/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  @Test
  public void GetAnEmployeeByIdWhenNotExist() throws Exception {
    when(employeeService.getEmployeeById(1L)).thenThrow(new RecordNotFoundException());

    String expectedResult = getEmployeeNotFoundJsonString();
    MvcResult result =
        mockMvc
            .perform(get("/employees/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();
    assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  // Todo: probably use gson and pojos to clean this up
  private Employee getTestEmployee() {
    return new Employee("Dan", "Abramov", "dan@gmail.com", 10000, "9959345033");
  }

  private String getTestEmployeeJsonString() {
    return "{\"id\":null,\"firstName\":\"Dan\",\"lastName\":\"Abramov\",\"email\":\"dan@gmail.com\",\"salary\":10000,\"phoneNumber\":\"9959345033\",\"updatedAt\":null,\"createdAt\":null}";
  }

  private String getEmployeeNotFoundJsonString() {
    return "{\"code\":\"RECORD_NOT_FOUND\",\"message\":\"The requested employee not found\"}";
  }
}
