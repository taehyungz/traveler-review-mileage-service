package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.PointReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PointReaderImpl implements PointReader {

    private final PointRepository pointRepository;

    @Override
    public Optional<Point> getByUser(String userId) {
        return pointRepository.findByUserId(userId);
    }

    @Override
    public Point findByUser(String userId) {
        // 모든 유저는 각자 하나의 point 엔티티를 가져야 한다
        return getByUser(userId)
                .orElseGet(() -> pointRepository.save(new Point(userId)));
    }
}
