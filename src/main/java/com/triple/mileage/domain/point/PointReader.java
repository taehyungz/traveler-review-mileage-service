package com.triple.mileage.domain.point;

public interface PointReader {
    Point findByUser(String userId);
}
