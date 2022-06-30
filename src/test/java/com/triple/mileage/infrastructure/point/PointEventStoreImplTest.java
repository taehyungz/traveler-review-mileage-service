package com.triple.mileage.infrastructure.point;

import com.triple.mileage.domain.point.Point;
import com.triple.mileage.domain.point.PointEvent;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.triple.mileage.domain.point.PointEvent.*;
import static org.mockito.ArgumentMatchers.any;

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

    @Nested
    @DisplayName("ReviewAddedTests")
    class ReviewAddedTests {
        @Test
        void Writing_plain_text_review_earns_one_point() {
            ReviewPointCommand plainTextReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);
            PointEvent pointEvent = new PointEvent(
                    plainTextReviewCommand.getReviewId(),
                    plainTextReviewCommand.getUserId(),
                    Reason.WRITE_REVIEW,
                    ONE_POINT,
                    point);
            Mockito.when(pointEventRepository.save(any(PointEvent.class))).thenReturn(pointEvent);

            sut.saveReviewAddedEvent(point, plainTextReviewCommand, Reason.WRITE_REVIEW);

            Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
        }

        @Test
        void Writing_photo_attached_review_earns_one_point() {
            ReviewPointCommand photoAddedReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), EMPTY_CONTENT);
            PointEvent pointEvent = new PointEvent(
                    photoAddedReviewCommand.getReviewId(),
                    photoAddedReviewCommand.getUserId(),
                    Reason.ATTACH_PHOTO,
                    ONE_POINT,
                    point);
            Mockito.when(pointEventRepository.save(any(PointEvent.class))).thenReturn(pointEvent);

            sut.saveReviewAddedEvent(point, photoAddedReviewCommand, Reason.ATTACH_PHOTO);

            Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
        }

        @Test
        void Writing_the_first_review_on_place_earns_one_point() {
            ReviewPointCommand firstPlaceReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
            PointEvent pointEvent = new PointEvent(
                    firstPlaceReviewCommand.getReviewId(),
                    firstPlaceReviewCommand.getUserId(),
                    Reason.FIRST_REVIEW,
                    ONE_POINT,
                    point);
            Mockito.when(pointEventRepository.save(any(PointEvent.class))).thenReturn(pointEvent);

            sut.saveReviewAddedEvent(point, firstPlaceReviewCommand, Reason.FIRST_REVIEW);

            Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
        }
    }

    @Nested
    @DisplayName("ReviewDeletedTests")
    class ReviewDeletedTests {
        @Test
        void Deleting_the_plain_text_review_deducts_one_point() {
            point.pointUp();
            PointEvent reviewDeletedEvent = new PointEvent(REVIEW_ID, USER_ID, Reason.DELETE_REVIEW, ONE_POINT, point);
            List<PointEvent> eventList = List.of(reviewDeletedEvent);
            final int pointAmount = point.getAmount();
            Mockito.when(pointEventRepository.saveAll(eventList)).thenReturn(eventList);

            sut.saveReviewDeletedEvent(eventList);

            Assertions.assertThat(point.getAmount()).isEqualTo(pointAmount - ONE_POINT);
        }

        @Test
        void Deleting_the_attached_text_review_deducts_two_point() {
            point.pointUp();
            point.pointUp();
            PointEvent reviewDeletedEvent = new PointEvent(REVIEW_ID, USER_ID, Reason.DELETE_REVIEW, ONE_POINT, point);
            PointEvent photoDeletedEvent = new PointEvent(REVIEW_ID, USER_ID, Reason.DELETE_PHOTO, ONE_POINT, point);
            List<PointEvent> eventList = List.of(reviewDeletedEvent, photoDeletedEvent);
            final int pointAmount = point.getAmount();
            Mockito.when(pointEventRepository.saveAll(eventList)).thenReturn(eventList);

            sut.saveReviewDeletedEvent(eventList);

            Assertions.assertThat(point.getAmount()).isEqualTo(pointAmount - 2 * ONE_POINT);
        }
    }
}