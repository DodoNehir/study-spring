package com.example.first.api.controller;

import com.example.first.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 어노테이션 기반, 생성자 주입 방식
 */
// RestController 로 이 클래스도 하나의 Bean 으로 등록
@RestController
public class TestController {

    // 생성자 주입 방식
    /*
    private final TestService testService;

    TestController(TestService testService) {
        this.testService = testService;
    }
     */

    // 필드 주입 방식
    // @Autowired 사용 시
    // @Qualifier 를 사용하거나 config에서 @Primary 등록해서 같은 Service Interface 중 어떤 걸 사용할 지 말해줌
    @Autowired
    @Qualifier("testService")
    private Service service;

    @GetMapping("/api/v1/test")
    public String test() {
        return service.getTest();
    }

}
