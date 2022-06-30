package com.triple.mileage.domain.review;

public interface ReviewStore {
    Review save(Review review);

    void delete(Review review);
}
