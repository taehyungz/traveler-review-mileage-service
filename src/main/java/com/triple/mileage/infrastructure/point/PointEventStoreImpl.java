package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.PointEventStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.triple.mileage.domain.point.PointEvent.*;

@Component
@RequiredArgsConstructor
public class PointEventStoreImpl implements PointEventStore {

    private final PointEventRepository pointEventRepository;

    private static final int ONE_POINT = 1;

    @Override
    public void saveReviewAddedEvent(Point point, ReviewPointCommand command, Reason reason) {
        PointEvent initPointEvent = new PointEvent(
                command.getReviewId(),
                command.getUserId(),
                reason,
                ONE_POINT,
                point);
        PointEvent pointEvent = pointEventRepository.save(initPointEvent);
        pointEvent.getPoint().pointUp();
    }

    @Override
    public void saveReviewDeletedEvent(List<PointEvent> reviewDeletedPointEvent) {
        List<PointEvent> pointEvents = pointEventRepository.saveAll(reviewDeletedPointEvent);
        pointEvents.get(0).getPoint().pointDownAmountOf(reviewDeletedPointEvent.size());
    }
}
