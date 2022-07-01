package com.triple.mileage.interfaces.event.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    REVIEW("리뷰");

    private final String description;
}
