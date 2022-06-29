package com.triple.mileage.domain.review;

public interface ReviewReader {
    boolean existsAnotherReviewInPlace(String placeId);
}
