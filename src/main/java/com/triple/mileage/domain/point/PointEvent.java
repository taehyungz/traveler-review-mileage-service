package com.triple.mileage.domain.point;

import com.triple.mileage.domain.BasicEntity;
import com.triple.mileage.domain.point.Point;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(
        name = "point_event",
        indexes = {
            @Index(name="idx_point_event_review_id", columnList = "reviewId"),
            @Index(name="idx_point_event_user_id", columnList = "userId"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointEvent extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_event_id")
    private Long id;

    @Column(nullable = false, length = 36)
    private String reviewId;

    @Column(nullable = false, length = 36)
    private String userId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Reason reason;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Long version = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id", nullable = false)
    private Point point;

    @Getter
    @RequiredArgsConstructor
    public enum Reason {
        WRITE_REVIEW("리뷰 작성"),
        ATTACH_PHOTO("사진 첨부"),
        FIRST_REVIEW("첫 리뷰"),
        REMOVE_REVIEW("리뷰 삭제"),
        DELETE_PHOTO("사진 삭제"),
        DELETE_FIRST_REVIEW("첫 리뷰 삭제"),
        ;

        private final String description;
    }

    public PointEvent(
            String reviewId,
            String userId,
            Reason reason,
            int amount,
            long version,
            Point point) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.reason = reason;
        this.amount = amount;
        this.version = version;
        this.point = point;
    }
}