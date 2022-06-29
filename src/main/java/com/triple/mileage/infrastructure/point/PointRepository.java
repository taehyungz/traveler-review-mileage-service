package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    Optional<Point> findByUserId(String userId);
}
