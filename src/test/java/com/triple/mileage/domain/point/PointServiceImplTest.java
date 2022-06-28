package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class PointServiceImplTest {
    @Mock
    private ReviewReader reviewReader;

    @Mock
    private ReviewStore reviewStore;

    @Mock
    private PointReader pointReader;

    @Mock
    private PointStore pointStore;

    @Mock
    private PointEventStore pointEventStore;

    @Mock
    private PhotoStore photoStore;

    @InjectMocks
    private PointServiceImpl pointService;

    private final int ZERO_POINT = 0;
    private final int ONE_POINT = 1;
    private final int THREE_POINT = 3;
    private final String USER_ID = "userId";
    private final String REVIEW_ID = "reviewId";
    private final String PLACE_ID = "placeId";
    private final String PHOTO_ID = "photoId";
    private final String CONTENT = "Good";
    private final String EMPTY_CONTENT = "";
    private final List<String> EMPTY_ATTACHED_PHOTO_LIST = Collections.emptyList();
    private final Review MY_REVIEW = new Review(REVIEW_ID, USER_ID, PLACE_ID);
    private final Review ANOTHER_REVIEW = new Review("anotherReview", "anotherUser", "anotherPlace");

    @Test
    void Plain_text_review_earns_one_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(List.of(ANOTHER_REVIEW));
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }

    @Test
    void Empty_text_review_earns_zero_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(List.of(ANOTHER_REVIEW));
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ZERO_POINT);
    }

    @Test
    void Photo_added_review_earns_one_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), EMPTY_CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(List.of(ANOTHER_REVIEW));
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);
        Mockito.when(reviewStore.save(any(Review.class))).thenReturn(MY_REVIEW);
        Mockito.doNothing().when(photoStore).save(any(Photo.class));

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }

    @Test
    void Photo_not_added_review_earns_zero_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(List.of(ANOTHER_REVIEW));
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);
        Mockito.when(reviewStore.save(any(Review.class))).thenReturn(MY_REVIEW);
        Mockito.doNothing().when(photoStore).save(any(Photo.class));

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ZERO_POINT);
    }

    @Test
    void First_place_review_earns_one_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(Collections.emptyList());
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ONE_POINT);
    }

    @Test
    void Non_first_place_review_earns_zero_point() {
        Point point = new Point(USER_ID);
        List<Review> anotherReviewList = List.of(ANOTHER_REVIEW);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(anotherReviewList);
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(ZERO_POINT);
    }

    @Test
    void Photo_added_plain_text_review_of_first_place_earns_three_point() {
        Point point = new Point(USER_ID);
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), CONTENT);
        Mockito.when(reviewReader.findByPlace(PLACE_ID)).thenReturn(Collections.emptyList());
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
        Mockito.doNothing().when(pointEventStore).save(any(PointEvent.class));
        Mockito.doNothing().when(pointStore).save(point);
        Mockito.when(reviewStore.save(any(Review.class))).thenReturn(MY_REVIEW);
        Mockito.doNothing().when(photoStore).save(any(Photo.class));

        pointService.earnsPointFromReviewAdded(reviewPointCommand);

        Assertions.assertThat(point.getAmount()).isEqualTo(THREE_POINT);
    }
}