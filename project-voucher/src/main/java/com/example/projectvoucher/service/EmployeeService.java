package com.example.projectvoucher.service;

import com.example.projectvoucher.entity.EmployeeEntity;
import com.example.projectvoucher.repository.EmployeeRepository;
import com.example.projectvoucher.request.EmployeeCreateRequest;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  // 사원 생성
  public Long createEmployee(EmployeeCreateRequest request) {
    EmployeeEntity employeeEntity = EmployeeEntity.from(request);
    employeeRepository.save(employeeEntity);
    return employeeEntity.getId();
  }

  // 사원 조회
  public EmployeeEntity findEmployeeById(Long employeeId) {
    Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
    if (employeeEntity.isPresent()) {
      EmployeeEntity employee = employeeEntity.get();
      return employee;
    }
    throw new RuntimeException("Employee not found");
  }
}
