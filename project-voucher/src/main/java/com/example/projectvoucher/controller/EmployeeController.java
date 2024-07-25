package com.example.projectvoucher.controller;

import com.example.projectvoucher.entity.EmployeeEntity;
import com.example.projectvoucher.request.EmployeeCreateRequest;
import com.example.projectvoucher.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/employees")
@RestController
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // 사원 생성
  @PostMapping
  public ResponseEntity<Long> createEmployee(@RequestBody EmployeeCreateRequest request) {
    return ResponseEntity.ok(employeeService.createEmployee(request));
  }

  // 사원 조회
  @GetMapping("/{no}")
  public ResponseEntity<EmployeeEntity> getAllEmployees(@PathVariable Long no) {
    return ResponseEntity.ok(employeeService.findEmployeeById(no));
  }
}
