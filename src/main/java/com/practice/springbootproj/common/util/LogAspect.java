package com.practice.springbootproj.common.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *  Spring AOP
 * </pre>
 * @author YooJaeYeong
 * @since 2021-03-28
 */
@Aspect
@Component
@Slf4j
public class LogAspect {


    //모든서비스의 모든 메서드
    @Around("execution(* *..*.*ServiceImpl.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
      log.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
      Object result = pjp.proceed();
      log.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
      return result;
    }
}
