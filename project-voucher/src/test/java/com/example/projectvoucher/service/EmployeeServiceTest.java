package com.example.projectvoucher.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.projectvoucher.entity.EmployeeEntity;
import com.example.projectvoucher.request.EmployeeCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceTest {

  @Autowired
  EmployeeService employeeService;

  @DisplayName("회원 생성 후 조회가 가능하다")
  @Test
  public void test1() {
    // given
    String name = "Alice";
    String position = "Boss";
    String department = "Engineering";

    // when
    EmployeeCreateRequest employeeCreateRequest = new EmployeeCreateRequest(name, position, department);
    Long no = employeeService.createEmployee(employeeCreateRequest);
    EmployeeEntity employeeEntity = employeeService.findEmployeeById(no);

    // then
    assertThat(employeeEntity).isNotNull();
    assertThat(employeeEntity.getName()).isEqualTo(name);
    assertThat(employeeEntity.getPosition()).isEqualTo(position);
    assertThat(employeeEntity.getDepartment()).isEqualTo(department);
  }

}