package com.triple.mileage.domain.point;

import com.triple.mileage.domain.point.dto.PointInfo;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;

public interface PointService {
    void earnsPointFromReviewAdded(ReviewPointCommand command);

    void deductPointFromReviewDeleted(ReviewPointCommand command);

    void modifyPointFromReviewModified(ReviewPointCommand command);

    int getUserPoint(String userId);

    PointInfo getUserPointWithEvents(String userId);
}
