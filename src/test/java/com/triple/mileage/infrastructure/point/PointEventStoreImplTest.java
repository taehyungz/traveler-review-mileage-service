package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PointEventStoreImplTest {

    @Mock
    PointEventRepository pointEventRepository;

    @InjectMocks
    PointEventStoreImpl sut;

    private Point point;
    private final String USER_ID = "userId";
    private final String REVIEW_ID = "reviewId";
    private final String PLACE_ID = "placeId";
    private final String PHOTO_ID = "photoId";
    private final String CONTENT = "Good";
    private final String EMPTY_CONTENT = "";
    private final List<String> EMPTY_ATTACHED_PHOTO_LIST = Collections.emptyList();
    private final int ONE_POINT = 1;

    @BeforeEach
    void init() {
        point = new Point("userId");
    }

    @Test
    void Writing_plain_text_review_earns_one_point() {
        ReviewPointCommand plainTextReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);

        sut.saveReviewWrittenEvent(point, plainTextReviewCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }

    @Test
    void Writing_photo_attached_review_earns_one_point() {
        ReviewPointCommand photoAddedReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), EMPTY_CONTENT);

        sut.savePhotoAttachedEvent(point, photoAddedReviewCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }

    @Test
    void Writing_the_first_review_on_place_earns_one_point() {
        ReviewPointCommand firstPlaceReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);

        sut.saveFirstReviewEvent(point, firstPlaceReviewCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }
}