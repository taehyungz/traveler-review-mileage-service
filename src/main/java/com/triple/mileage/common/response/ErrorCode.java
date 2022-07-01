package com.triple.mileage.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ILLEGAL_STATUS("잘못된 상태값입니다.");

    private final String errorMsg;
}
