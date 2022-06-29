package com.triple.mileage.domain.review;

import com.triple.mileage.domain.point.dto.ReviewPointCommand;

public interface ReviewStore {
    Review save(ReviewPointCommand command);
}
