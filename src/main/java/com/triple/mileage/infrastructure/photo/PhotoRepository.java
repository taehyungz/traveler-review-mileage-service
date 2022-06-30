package com.triple.mileage.infrastructure.photo;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {
    void deleteAllByReview(Review review);
}
