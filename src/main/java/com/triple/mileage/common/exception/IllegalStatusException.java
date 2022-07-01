package com.triple.mileage.common.exception;

import com.triple.mileage.common.response.ErrorCode;

public class IllegalStatusException extends BaseException {

    public IllegalStatusException() {
        super(ErrorCode.ILLEGAL_STATUS);
    }

    public IllegalStatusException(String errorMsg) {
        super(errorMsg, ErrorCode.ILLEGAL_STATUS);
    }
}
