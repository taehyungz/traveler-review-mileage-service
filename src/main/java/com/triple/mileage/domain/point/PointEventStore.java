package com.triple.mileage.domain.point;

public interface PointEventStore {
    void save(PointEvent pointEvent);
}
