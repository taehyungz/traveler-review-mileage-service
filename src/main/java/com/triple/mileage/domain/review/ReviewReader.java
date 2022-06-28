package com.triple.mileage.domain.review;

import java.util.List;

public interface ReviewReader {
    List<Review> findByPlace(String placeId);
}
