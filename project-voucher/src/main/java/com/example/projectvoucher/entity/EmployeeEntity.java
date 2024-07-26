package com.example.projectvoucher.entity;

import com.example.projectvoucher.request.EmployeeCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "employee")
@Entity
public class EmployeeEntity extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String position;

  @Column(nullable = false)
  private String department;

  public EmployeeEntity() {

  }

  public EmployeeEntity(String name, String position, String department) {
    this.name = name;
    this.position = position;
    this.department = department;
  }

  public static EmployeeEntity from(EmployeeCreateRequest employeeCreateRequest) {
    EmployeeEntity employeeEntity = new EmployeeEntity();
    employeeEntity.setName(employeeCreateRequest.name());
    employeeEntity.setPosition(employeeCreateRequest.position());
    employeeEntity.setDepartment(employeeCreateRequest.department());
    return employeeEntity;
  }

}
