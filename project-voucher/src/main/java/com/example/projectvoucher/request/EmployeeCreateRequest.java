package com.example.projectvoucher.request;

import com.example.projectvoucher.response.EmployeeResponse;

public record EmployeeCreateRequest(
    String name,
    String position,
    String department
) {
  public static EmployeeCreateRequest of(String name, String position, String department) {
    return new EmployeeCreateRequest(name, position, department);
  }

}
