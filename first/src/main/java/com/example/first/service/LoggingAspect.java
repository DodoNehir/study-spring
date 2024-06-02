package com.example.first.service;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Aspect
@Component
public class LoggingAspect {

    @Around("within(com.example.first.api.controller.ProductController)")
    public void doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        ZonedDateTime startAt = ZonedDateTime.now();
        System.out.println("메소드 시작...");

        // 본 메소드 수행
        joinPoint.proceed();

        ZonedDateTime finishAt = ZonedDateTime.now();
        System.out.println("메소드 종료. 수행 시간은 " +
                (finishAt.getNano() - startAt.getNano()) / 1000000 + " ms");
    }


}
