package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.dto.PointEventInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointEventRepository extends JpaRepository<PointEvent, Long> {
    List<PointEvent> findAllByReviewId(String reviewId);

    @Query(value =
            "SELECT " +
            " new com.triple.mileage.domain.point.dto.PointEventInfo(" +
                    "pe.reviewId, " +
                    "pe.actionType, " +
                    "sum(pe.amount)) " +
            "FROM PointEvent pe " +
            "WHERE pe.userId = :userId " +
            "GROUP BY " +
            " pe.version, pe.reviewId, pe.actionType"
    )
    List<PointEventInfo> findAllByUserIdGroupByVersion(String userId);
}
