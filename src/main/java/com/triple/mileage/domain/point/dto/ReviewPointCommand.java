package com.triple.mileage.domain.point.dto;

import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
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
}
