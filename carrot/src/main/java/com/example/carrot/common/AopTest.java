package com.example.carrot.common;
/**
 *      aop注解实战
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AopTest {

    @Pointcut("execution(* com.example.carrot.service.UserService..*(..))")
    public void aspect(){}

    @Before("aspect()")
    public void before(JoinPoint joinPoint){
        log.info("-----before "+ joinPoint);
    }
    @After("aspect()")
    public void after(JoinPoint joinPoint){
        log.info("-----after "+ joinPoint);
    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint){
        long start = System.currentTimeMillis();
        try {
            Object proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            log.info("-----around "+ joinPoint+"\t use time "+(end-start)+"ms");
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
