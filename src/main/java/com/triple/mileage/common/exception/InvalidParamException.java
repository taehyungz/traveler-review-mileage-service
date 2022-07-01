package com.triple.mileage.common.exception;

import com.triple.mileage.common.response.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {
        super(ErrorCode.INVALID_PARAMETER);
    }

    public InvalidParamException(String errorMsg) {
        super(errorMsg, ErrorCode.INVALID_PARAMETER);
    }
}
