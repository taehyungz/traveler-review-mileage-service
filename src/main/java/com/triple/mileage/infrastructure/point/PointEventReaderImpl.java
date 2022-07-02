package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.PointEventReader;
import com.triple.mileage.domain.point.dto.PointEventInfo;
import com.triple.mileage.domain.point.dto.TotalPointEventInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointEventReaderImpl implements PointEventReader {

    private final PointEventRepository pointEventRepository;

    @Override
    public List<PointEvent> findAllEventsByReviewId(String reviewId) {
        return pointEventRepository.findAllByReviewId(reviewId);
    }

    @Override
    public List<PointEventInfo> findAllEventsByUserId(String userId) {
        return pointEventRepository.findAllByUserIdGroupByVersion(userId);
    }

    @Override
    public List<TotalPointEventInfo> findAllEvents() {
        return  pointEventRepository.findAllByGroupByVersion();
    }
}
