package com.triple.mileage.interfaces.event.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    ADD("추가"),
    MOD("수정"),
    DELETE("삭제");

    private final String description;
}
