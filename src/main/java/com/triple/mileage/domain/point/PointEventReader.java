package com.triple.mileage.domain.point;

import com.triple.mileage.domain.point.dto.PointEventInfo;

import java.util.List;

public interface PointEventReader {

    List<PointEvent> findAllEventsByReviewId(String reviewId);

    List<PointEventInfo> findAllEventsByUserId(String userId);
}
