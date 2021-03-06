package com.triple.mileage.domain.point;

import com.triple.mileage.common.exception.IllegalStatusException;
import com.triple.mileage.domain.BasicEntity;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
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
    private ActionType actionType;

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

    public PointEvent(
            String reviewId,
            String userId,
            ActionType actionType,
            Reason reason,
            int amount,
            Point point) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.actionType = actionType;
        this.reason = reason;
        this.amount = amount;
        this.version = point.getVersion();
        this.point = point;
    }

    public static PointEvent deductPointEventFrom(Point point, PointEvent pointEvent, ActionType actionType) {
        return new PointEvent(
                pointEvent.getReviewId(),
                pointEvent.getUserId(),
                actionType,
                pointEvent.getReason().getOppositeReason(),
                pointEvent.getAmount() * -1,
                point
        );
    }

    public static PointEvent of(Point point, ReviewPointCommand command, Reason reason, ActionType actionType) {
        return new PointEvent(
                command.getReviewId(),
                command.getUserId(),
                actionType,
                reason,
                1,
                point
        );
    }

    @Getter
    @RequiredArgsConstructor
    public enum Reason {
        WRITE_TEXT("????????? ??????"),
        ATTACH_PHOTO("?????? ??????"),
        FIRST_REVIEW("??? ?????? ??????"),
        DELETE_TEXT("????????? ??????"),
        DELETE_PHOTO("?????? ??????"),
        DELETE_FIRST_REVIEW("??? ?????? ??????");

        private final String description;

        public Reason getOppositeReason() {
            switch (this) {
                case WRITE_TEXT:
                    return DELETE_TEXT;
                case ATTACH_PHOTO:
                    return DELETE_PHOTO;
                case FIRST_REVIEW:
                    return DELETE_FIRST_REVIEW;
                default:
                    throw new IllegalStatusException("??????????????? ???????????????.");
            }
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum ActionType {
        ADD("??????"),
        MOD("??????"),
        DELETE("??????");

        private final String description;
    }
}