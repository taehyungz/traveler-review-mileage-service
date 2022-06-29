package com.triple.mileage.infrastructure.review;

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
}
