package com.sistore.productservice.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Component
public class Logging {

    @Before("execution(public org.springframework.http.ResponseEntity com.sistore.productservice.controller.ProductController.saveProduct(..))")
    public void logProductRequest(){
        System.out.println("Logging before save Product method----------------------------");
    }

    @Around("@within(org.springframework.stereotype.Service)")
    public Object logMethodCompletion(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Method Execution started successfully---------" + LocalDateTime.now());
        Object result = joinPoint.proceed();
        System.out.println("Method execution ended successfully---------" + LocalDateTime.now());
        return result;
    }

    @After("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logExecutionTime(){
        System.out.println("product deleted successfully------------------");
    }

    @Around("@annotation(com.sistore.productservice.logging.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) {
        System.out.println("Method Execution started successfully---------");
        LocalDateTime start = LocalDateTime.now();
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
        System.out.println("Method execution ended successfully---------" );
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start,end);
        System.out.println("Total execution time taken --------" + duration.toMillis() + " ms.");
        return result;
    }

}
