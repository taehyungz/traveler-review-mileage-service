package com.triple.mileage.domain.point;

import java.util.List;

public interface PointEventReader {

    List<PointEvent> findAllEventsByReviewId(String reviewId);
}
