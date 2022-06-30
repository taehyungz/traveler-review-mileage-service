package com.triple.mileage.domain.point;

import com.triple.mileage.domain.point.dto.ReviewPointCommand;

import java.util.List;

import static com.triple.mileage.domain.point.PointEvent.*;

public interface PointEventStore {
    void saveReviewAddedEvent(Point point, ReviewPointCommand command, Reason reason);

    void saveReviewDeletedEvent(List<PointEvent> pointEventList);
}
