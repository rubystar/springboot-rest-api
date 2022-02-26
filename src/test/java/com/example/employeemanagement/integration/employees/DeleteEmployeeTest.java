package com.example.employeemanagement.integration.employees;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.employeemanagement.controllers.EmployeeController;
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
public class DeleteEmployeeTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private EmployeeRepository employeeRepository;
  @MockBean private EmployeeService employeeService;

  @Test
  public void deleteAnEmployeeByIdWhenExists() throws Exception {
    when(employeeService.deleteEmployee(1L))
        .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    MvcResult result =
        mockMvc
            .perform(delete("/employees/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
    assertEquals("", result.getResponse().getContentAsString());
  }

  @Test
  public void deleteAnEmployeeByIdWhenNotExist() throws Exception {
    when(employeeService.deleteEmployee(1L)).thenThrow(new RecordNotFoundException());

    String expectedResult = getEmployeeNotFoundJsonString();
    MvcResult result =
        mockMvc
            .perform(delete("/employees/1").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();
    assertEquals(expectedResult, result.getResponse().getContentAsString());
  }

  // Todo: probably use gson and pojos to clean this up
  private String getEmployeeNotFoundJsonString() {
    return "{\"code\":\"RECORD_NOT_FOUND\",\"message\":\"The requested employee not found\"}";
  }
}
