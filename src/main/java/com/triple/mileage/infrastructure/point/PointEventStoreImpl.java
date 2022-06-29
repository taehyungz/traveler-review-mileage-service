package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.PointEventStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointEventStoreImpl implements PointEventStore {

    private final PointEventRepository pointEventRepository;
    private static final int ONE_POINT = 1;

    @Override
    public void saveReviewWrittenEvent(Point point, ReviewPointCommand command) {
        PointEvent pointEvent = new PointEvent(
                command.getReviewId(),
                command.getUserId(),
                PointEvent.Reason.WRITE_REVIEW,
                ONE_POINT,
                point.getVersion(),
                point);
        point.pointUp();
        pointEventRepository.save(pointEvent);
    }

    @Override
    public void savePhotoAttachedEvent(Point point, ReviewPointCommand command) {
        PointEvent pointEvent = new PointEvent(
                command.getReviewId(),
                command.getUserId(),
                PointEvent.Reason.ATTACH_PHOTO,
                ONE_POINT,
                point.getVersion(),
                point);
        point.pointUp();
        pointEventRepository.save(pointEvent);
    }

    @Override
    public void saveFirstReviewEvent(Point point, ReviewPointCommand command) {
        PointEvent pointEvent = new PointEvent(
                command.getReviewId(),
                command.getUserId(),
                PointEvent.Reason.FIRST_REVIEW,
                ONE_POINT,
                point.getVersion(),
                point);
        point.pointUp();
        pointEventRepository.save(pointEvent);
    }
}
