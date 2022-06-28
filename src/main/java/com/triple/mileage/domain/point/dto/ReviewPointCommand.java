package com.triple.mileage.domain.point.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReviewPointCommand {
    private final String reviewId;

    private final String userId;

    private final String placeId;

    private final List<String> attachedPhotoIds;

    private final String content;
}
