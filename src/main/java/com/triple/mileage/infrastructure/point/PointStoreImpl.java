package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.PointStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointStoreImpl implements PointStore {

    private final PointRepository pointRepository;

    @Override
    public Point save(Point point) {
        return pointRepository.save(point);
    }
}
