package com.example.projectvoucher.entity;

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
public class EmployeeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

}
