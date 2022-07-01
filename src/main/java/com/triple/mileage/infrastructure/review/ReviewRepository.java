package com.triple.mileage.infrastructure.review;

import com.triple.mileage.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByPlaceId(String placeId);

    boolean existsByUserIdAndPlaceId(String userId, String placeId);
}
