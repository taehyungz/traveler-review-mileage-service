package com.triple.mileage.infrastructure.review;

import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {

    private final ReviewRepository reviewRepository;

    @Override
    public List<Review> findByPlace(String placeId) {
        return reviewRepository.findAllByPlaceId(placeId);
    }
}
