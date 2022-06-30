package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.PointEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointEventRepository extends JpaRepository<PointEvent, Long> {
    List<PointEvent> findAllByReviewId(String reviewId);
}
