package com.triple.mileage.infrastructure.review;

import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewStoreImpl implements ReviewStore {

    private final ReviewRepository reviewRepository;

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void delete(Review review) {
        reviewRepository.delete(review);
    }
}
