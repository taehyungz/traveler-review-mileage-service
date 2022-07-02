package com.triple.mileage.domain.point.dto;

import com.triple.mileage.domain.point.PointEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TotalPointEventInfo {
    private final String reviewId;

    private final String userId;

    private final PointEvent.ActionType actionType;

    private final long amount;
}
