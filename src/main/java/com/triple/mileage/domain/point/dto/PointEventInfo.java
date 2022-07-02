package com.triple.mileage.domain.point.dto;

import com.triple.mileage.domain.point.PointEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PointEventInfo {
    private final String reviewId;

    private final PointEvent.ActionType actionType;

    private final long amount;

    public PointEventInfo(PointEvent pointEvent) {
        this.reviewId = pointEvent.getReviewId();
        this.actionType = pointEvent.getActionType();
        this.amount = pointEvent.getAmount();
    }
}
