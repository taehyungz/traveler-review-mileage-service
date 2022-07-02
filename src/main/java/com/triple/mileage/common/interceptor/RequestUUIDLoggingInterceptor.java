package com.triple.mileage.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Component
public class RequestUUIDLoggingInterceptor implements HandlerInterceptor {

    public static final String TRACE_ID = "trace_id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(TRACE_ID, createTraceId());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }

    private String createTraceId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
