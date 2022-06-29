package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PointServiceImplTest {
    @Mock
    private ReviewReader reviewReader;

    @Mock
    private ReviewStore reviewStore;

    @Mock
    private PointReader pointReader;

    @Mock
    private PointEventStore pointEventStore;

    @Mock
    private PhotoStore photoStore;

    @InjectMocks
    private PointServiceImpl sut;

    private final String USER_ID = "userId";
    private final String REVIEW_ID = "reviewId";
    private final String PLACE_ID = "placeId";
    private final String PHOTO_ID = "photoId";
    private final String CONTENT = "Good";
    private final String EMPTY_CONTENT = "";
    private final List<String> EMPTY_ATTACHED_PHOTO_LIST = Collections.emptyList();
    private Point point;

    @BeforeEach
    void init() {
        point = new Point(USER_ID);
        Mockito.when(pointReader.findByUser(USER_ID)).thenReturn(point);
    }

    @Test
    void Plain_text_review_earns_one_point() {
        ReviewPointCommand plainTextReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

        sut.earnsPointFromReviewAdded(plainTextReviewCommand);

        verify(pointEventStore, times(1)).saveReviewWrittenEvent(point, plainTextReviewCommand);
        verify(pointEventStore, never()).savePhotoAttachedEvent(point, plainTextReviewCommand);
        verify(pointEventStore, never()).saveFirstReviewEvent(point, plainTextReviewCommand);
    }

    @Test
    void Empty_text_review_earns_zero_point() {
        ReviewPointCommand emptyTextReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

        sut.earnsPointFromReviewAdded(emptyTextReviewCommand);

        verify(pointEventStore, never()).saveReviewWrittenEvent(point, emptyTextReviewCommand);
        verify(pointEventStore, never()).savePhotoAttachedEvent(point, emptyTextReviewCommand);
        verify(pointEventStore, never()).saveFirstReviewEvent(point, emptyTextReviewCommand);
    }

    @Test
    void Photo_added_review_earns_one_point() {
        ReviewPointCommand photoAddedReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), EMPTY_CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

        sut.earnsPointFromReviewAdded(photoAddedReviewCommand);

        verify(pointEventStore, never()).saveReviewWrittenEvent(point, photoAddedReviewCommand);
        verify(pointEventStore, times(1)).savePhotoAttachedEvent(point, photoAddedReviewCommand);
        verify(pointEventStore, never()).saveFirstReviewEvent(point, photoAddedReviewCommand);
    }

    @Test
    void Photo_not_added_review_earns_zero_point() {
        ReviewPointCommand photoNotAddedReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

        sut.earnsPointFromReviewAdded(photoNotAddedReviewCommand);

        verify(pointEventStore, never()).saveReviewWrittenEvent(point, photoNotAddedReviewCommand);
        verify(pointEventStore, never()).savePhotoAttachedEvent(point, photoNotAddedReviewCommand);
        verify(pointEventStore, never()).saveFirstReviewEvent(point, photoNotAddedReviewCommand);
    }

    @Test
    void First_place_review_earns_one_point() {
        ReviewPointCommand firstPlaceReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);

        sut.earnsPointFromReviewAdded(firstPlaceReviewCommand);

        verify(pointEventStore, never()).saveReviewWrittenEvent(point, firstPlaceReviewCommand);
        verify(pointEventStore, never()).savePhotoAttachedEvent(point, firstPlaceReviewCommand);
        verify(pointEventStore, times(1)).saveFirstReviewEvent(point, firstPlaceReviewCommand);
    }

    @Test
    void Non_first_place_review_earns_zero_point() {
        ReviewPointCommand nonFirstReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

        sut.earnsPointFromReviewAdded(nonFirstReviewCommand);

        verify(pointEventStore, never()).saveReviewWrittenEvent(point, nonFirstReviewCommand);
        verify(pointEventStore, never()).savePhotoAttachedEvent(point, nonFirstReviewCommand);
        verify(pointEventStore, never()).saveFirstReviewEvent(point, nonFirstReviewCommand);
    }

    @Test
    void Photo_added_plain_text_review_of_first_place_earns_three_point() {
        ReviewPointCommand threePointReviewCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), CONTENT);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);

        sut.earnsPointFromReviewAdded(threePointReviewCommand);

        verify(pointEventStore, times(1)).saveReviewWrittenEvent(point, threePointReviewCommand);
        verify(pointEventStore, times(1)).savePhotoAttachedEvent(point, threePointReviewCommand);
        verify(pointEventStore, times(1)).saveFirstReviewEvent(point, threePointReviewCommand);
    }

    @Test
    void Adding_a_review_saves_review_with_photo() {
        ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), CONTENT);
        Review myReview = new Review(REVIEW_ID, USER_ID, PLACE_ID);
        Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);
        Mockito.when(reviewStore.save(reviewPointCommand)).thenReturn(myReview);

        sut.earnsPointFromReviewAdded(reviewPointCommand);

        verify(reviewStore, times(1)).save(reviewPointCommand);
        verify(photoStore, times(1)).save(reviewPointCommand.getAttachedPhotoIds(), myReview);
    }
}