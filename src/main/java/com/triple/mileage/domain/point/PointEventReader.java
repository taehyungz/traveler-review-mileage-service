package com.triple.mileage.domain.point;

import com.triple.mileage.domain.point.dto.PointEventInfo;
import com.triple.mileage.domain.point.dto.TotalPointEventInfo;

import java.util.List;

public interface PointEventReader {

    List<PointEvent> findAllEventsByReviewId(String reviewId);

    List<PointEventInfo> findAllEventsByUserId(String userId);

    List<TotalPointEventInfo> findAllEvents();
}
