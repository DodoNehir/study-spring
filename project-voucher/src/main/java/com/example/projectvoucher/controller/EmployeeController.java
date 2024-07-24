package com.example.projectvoucher.controller;

import com.example.projectvoucher.request.EmployeeCreateRequest;
import com.example.projectvoucher.response.EmployeeResponse;
import java.util.HashMap;
import java.util.Map;
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

  // DB 는 쿼리 날릴 때 where 조건이 필요한데 여기서는 사번이라고 생각해보자
  private final Map<Long, EmployeeCreateRequest> employeeResponseMap = new HashMap<>();

  // 회원 생성
  @PostMapping
  public Long createEmployee(@RequestBody EmployeeCreateRequest request) {
    Long no = employeeResponseMap.size() + 1L;
    employeeResponseMap.put(no, request);
    return no;
  }

  // 회원 조회
  @GetMapping("/{no}")
  public ResponseEntity<EmployeeCreateRequest> getAllEmployees(@PathVariable Long no) {
    EmployeeCreateRequest employeeCreateRequest = employeeResponseMap.get(no);
    return ResponseEntity.ok(employeeCreateRequest);
  }
}
