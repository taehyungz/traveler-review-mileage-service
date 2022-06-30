package com.triple.mileage.domain.photo;

import com.triple.mileage.domain.review.Review;

import java.util.List;

public interface PhotoStore {
    void save(List<String> attachedPhotoIds, Review savedReview);

    void deleteAllByReview(Review review);
}
