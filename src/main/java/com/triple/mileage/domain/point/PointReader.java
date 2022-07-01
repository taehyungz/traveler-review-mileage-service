package com.triple.mileage.domain.point;

import java.util.Optional;

public interface PointReader {
    Optional<Point> getByUser(String userId);

    Point findByUser(String userId);
}
