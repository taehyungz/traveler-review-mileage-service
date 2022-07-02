package com.triple.mileage.common.response;

import com.triple.mileage.common.exception.BaseException;
import com.triple.mileage.common.interceptor.RequestUUIDLoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    // 200, base exception
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {BaseException.class})
    public CommonResponse onBaseException(BaseException e) {
        String requestId = MDC.get(RequestUUIDLoggingInterceptor.REQUEST_ID);
        log.warn("[BaseException] requestId = {}, cause = {}, errorMsg = {}",
                requestId,
                NestedExceptionUtils.getMostSpecificCause(e),
                NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(e.getMessage(), e.getErrorCode().name());
    }

    // 400, invalid parameter error
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public CommonResponse requestParamNotValidException(MethodArgumentNotValidException e) {
        String eventId = MDC.get(RequestUUIDLoggingInterceptor.REQUEST_ID);
        log.warn("[requestParamNotValidException] requestId = {}, errorMsg = {}",
                eventId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        BindingResult bindingResult = e.getBindingResult();
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            String message = "Request Error" + " " + fe.getField() + "=" + fe.getRejectedValue() + " (" + fe.getDefaultMessage() + ")";
            return CommonResponse.fail(message, ErrorCode.INVALID_PARAMETER.name());
        } else {
            return CommonResponse.fail(ErrorCode.INVALID_PARAMETER.getErrorMsg(), ErrorCode.INVALID_PARAMETER.name());
        }
    }

    // 500, system error
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
        String requestId = MDC.get(RequestUUIDLoggingInterceptor.REQUEST_ID);
        log.error("requestId = {} ", requestId, e);
        return CommonResponse.fail(ErrorCode.SYSTEM_ERROR);
    }
}
