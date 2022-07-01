package com.triple.mileage.infrastructure.review;

import com.triple.mileage.common.exception.EntityNotFoundException;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

    private final ReviewRepository reviewRepository;

    @Override
    public boolean existsAnotherReviewInPlace(String placeId) {
        return reviewRepository.existsByPlaceId(placeId);
    }

    @Override
    public Review findReview(String reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean isAlreadyWittenReviewInPlace(String userId, String placeId) {
        return reviewRepository.existsByUserIdAndPlaceId(userId, placeId);
    }
}
