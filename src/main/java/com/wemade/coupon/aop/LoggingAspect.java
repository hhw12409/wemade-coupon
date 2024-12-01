package com.wemade.coupon.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
  private final String ANNOTATION_LOGGER_TARGET = "@annotation(com.wemade.coupon.annotation.LoggerTarget)";
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Before(ANNOTATION_LOGGER_TARGET)
  public void loggingBefore(JoinPoint joinPoint) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    log.info("## REQUEST [{}] {} {}, Payload: {}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), getPayload(joinPoint));
  }

  @AfterReturning(value = ANNOTATION_LOGGER_TARGET, returning = "result")
  public void afterReturn(Object result) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    log.info("## RESPONSE [{}] {} {}, Result: {}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), result.toString());
  }

  @AfterThrowing(value = ANNOTATION_LOGGER_TARGET, throwing = "e")
  public void loggingAfterThrowing(Exception e) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    log.info("## RESPONSE [{}] {} {}, Exception: {}", request.getRemoteAddr(), request.getMethod(), request.getRequestURI(), e.toString());
  }

  private String getPayload(JoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    try {
      return objectMapper.writeValueAsString(args);
    } catch (IOException e) {
      log.error("request payload 에러 발생", e);
      return "request payload 에러 발생";
    }
  }
}
