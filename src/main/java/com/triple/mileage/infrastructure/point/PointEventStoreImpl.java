package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.PointEventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointEventStoreImpl implements PointEventStore {

    private final PointEventRepository pointEventRepository;

    @Override
    public void save(PointEvent pointEvent) {
        pointEventRepository.save(pointEvent);
    }
}
