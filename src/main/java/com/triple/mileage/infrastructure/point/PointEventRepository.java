package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.PointEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointEventRepository extends JpaRepository<PointEvent, Long> {
}
