package com.example.projectvoucher.response;

public record EmployeeResponse(
    Long id,
    String name,
    String position,
    String department
) {
  public static EmployeeResponse of(Long id, String name, String position, String department) {
    return new EmployeeResponse(id, name, position, department);
  }

}
