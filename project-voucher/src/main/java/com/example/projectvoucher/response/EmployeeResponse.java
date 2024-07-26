package com.example.projectvoucher.response;

import com.example.projectvoucher.entity.EmployeeEntity;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record EmployeeResponse(
    Long id,
    String name,
    String position,
    String department,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

  public static EmployeeResponse from(EmployeeEntity employeeEntity) {
    return new EmployeeResponse(
        employeeEntity.getId(),
        employeeEntity.getName(),
        employeeEntity.getPosition(),
        employeeEntity.getDepartment(),
        employeeEntity.getCreatedAt(),
        employeeEntity.getUpdatedAt()
    );
  }

}
