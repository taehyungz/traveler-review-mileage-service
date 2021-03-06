package com.triple.mileage.domain.review;

public interface ReviewReader {
    boolean existsAnotherReviewInPlace(String placeId);

    Review findReview(String reviewId);

    boolean isAlreadyWittenReviewInPlace(String userId, String placeId);
}
