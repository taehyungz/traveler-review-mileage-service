package com.triple.mileage.infrastructure.review;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ReviewReaderImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    ReviewReaderImpl sut;

    @Test
    void If_another_review_exists_on_place_then_return_true() {
        String placeId = "placeId";
        Mockito.when(reviewRepository.existsByPlaceId(placeId)).thenReturn(true);

        boolean actual = sut.existsAnotherReviewInPlace(placeId);

        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void If_no_review_exists_on_place_then_return_false() {
        String placeId = "placeId";
        Mockito.when(reviewRepository.existsByPlaceId(placeId)).thenReturn(false);

        boolean actual = sut.existsAnotherReviewInPlace(placeId);

        Assertions.assertThat(actual).isFalse();
    }
}