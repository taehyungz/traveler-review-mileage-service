package com.triple.mileage.common.exception;

import com.triple.mileage.common.response.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException() {
        super(ErrorCode.INVALID_PARAMETER);
    }
}
