package com.triple.mileage.domain.point.dto;

import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.List;

@Getter
@ToString
public class ReviewPointCommand {
    private final String reviewId;

    private final String userId;

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
        Assert.hasText(reviewId, "리뷰 아이디가 빈 문자열입니다");
        Assert.hasText(userId, "유저 아이디가 빈 문자열입니다");
        Assert.hasText(placeId, "장소 아이디가 빈 문자열입니다");
        this.reviewId = reviewId;
        this.userId = userId;
        this.placeId = placeId;
        this.attachedPhotoIds = attachedPhotoIds;
        this.content = content;
    }
}
