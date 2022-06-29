package com.triple.mileage.domain.point;

import com.triple.mileage.domain.point.dto.ReviewPointCommand;

public interface PointEventStore {
    void saveReviewWrittenEvent(Point point, ReviewPointCommand command);

    void savePhotoAttachedEvent(Point point, ReviewPointCommand command);

    void saveFirstReviewEvent(Point point, ReviewPointCommand command);
}
