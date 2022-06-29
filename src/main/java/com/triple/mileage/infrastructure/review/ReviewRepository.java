package com.triple.mileage.infrastructure.review;

import com.triple.mileage.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findAllByPlaceId(String placeId);
}
