package com.triple.mileage.domain.point.dto;

import com.triple.mileage.common.validation.ConstraintViolationValidateUtil;
import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@ToString
public class ReviewPointCommand {
    @NotEmpty
    private final String reviewId;

    @NotEmpty
    private final String userId;

    @NotEmpty
    private final String placeId;

    private final List<String> attachedPhotoIds;

    private final String content;

    public static ReviewPointCommand from(EventRequest eventRequest) {
        return new ReviewPointCommand(
                eventRequest.getReviewId(),
                eventRequest.getUserId(),
                eventRequest.getPlaceId(),
                eventRequest.getAttachedPhotoIds(),
                eventRequest.getContent()
        );
    }

    public ReviewPointCommand(
            String reviewId,
            String userId,
            String placeId,
            List<String> attachedPhotoIds,
            String content) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.placeId = placeId;
        this.attachedPhotoIds = attachedPhotoIds;
        this.content = content;
        ConstraintViolationValidateUtil.validate(this);
    }
}
