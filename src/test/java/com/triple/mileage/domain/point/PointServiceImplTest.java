package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.triple.mileage.domain.point.PointEvent.*;
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
    private PointEventReader pointEventReader;

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

    @Nested
    @DisplayName("ReviewAddedTests")
        class ReviewAddedTests {
        @Test
        void Plain_text_review_earns_one_point() {
            ReviewPointCommand plainTextReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

            sut.earnsPointFromReviewAdded(plainTextReviewCommand);

            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, plainTextReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, plainTextReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, plainTextReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Empty_text_review_earns_zero_point() {
            ReviewPointCommand emptyTextReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

            sut.earnsPointFromReviewAdded(emptyTextReviewCommand);

            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, emptyTextReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, emptyTextReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, emptyTextReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Photo_added_review_earns_one_point() {
            ReviewPointCommand photoAddedReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), EMPTY_CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

            sut.earnsPointFromReviewAdded(photoAddedReviewCommand);

            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, photoAddedReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, photoAddedReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, photoAddedReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Photo_not_added_review_earns_zero_point() {
            ReviewPointCommand photoNotAddedReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

            sut.earnsPointFromReviewAdded(photoNotAddedReviewCommand);

            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, photoNotAddedReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, photoNotAddedReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, photoNotAddedReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void First_place_review_earns_one_point() {
            ReviewPointCommand firstPlaceReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);

            sut.earnsPointFromReviewAdded(firstPlaceReviewCommand);

            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, firstPlaceReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, firstPlaceReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, firstPlaceReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Non_first_place_review_earns_zero_point() {
            ReviewPointCommand nonFirstReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, EMPTY_CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(true);

            sut.earnsPointFromReviewAdded(nonFirstReviewCommand);

            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, nonFirstReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, nonFirstReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, never())
                    .saveReviewAddedEvent(point, nonFirstReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Photo_added_plain_text_review_of_first_place_earns_three_point() {
            ReviewPointCommand threePointReviewCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), CONTENT);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);

            sut.earnsPointFromReviewAdded(threePointReviewCommand);

            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, threePointReviewCommand, Reason.WRITE_TEXT);
            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, threePointReviewCommand, Reason.ATTACH_PHOTO);
            verify(pointEventStore, times(1))
                    .saveReviewAddedEvent(point, threePointReviewCommand, Reason.FIRST_REVIEW);
        }

        @Test
        void Adding_a_review_saves_review_with_photo() {
            ReviewPointCommand reviewPointCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, List.of(PHOTO_ID), CONTENT);
            Review myReview = new Review(REVIEW_ID, USER_ID, PLACE_ID);
            Mockito.when(reviewReader.existsAnotherReviewInPlace(PLACE_ID)).thenReturn(false);
            Mockito.when(reviewStore.save(any(Review.class))).thenReturn(myReview);

            sut.earnsPointFromReviewAdded(reviewPointCommand);

            verify(reviewStore, times(1)).save(any(Review.class));
            verify(photoStore, times(1))
                    .save(reviewPointCommand.getAttachedPhotoIds(), myReview);
        }
    }

    @Nested
    @DisplayName("ReviewDeletedTests")
    class ReviewDeletedTests {
        @Test
        void Deleting_a_review_saves_review_deleted_event() {
            ReviewPointCommand reviewDeletedCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);

            sut.deductPointFromReviewDeleted(reviewDeletedCommand);

            verify(pointEventStore, times(1))
                    .saveReviewDeletedEvent(ArgumentMatchers.anyList());
        }

        @Test
        void Deleting_a_review_deletes_photos_and_review() {
            ReviewPointCommand reviewDeletedCommand = new ReviewPointCommand(
                    REVIEW_ID, USER_ID, PLACE_ID, EMPTY_ATTACHED_PHOTO_LIST, CONTENT);
            Review review = new Review(REVIEW_ID, USER_ID, PLACE_ID);
            Mockito.when(reviewReader.findReview(reviewDeletedCommand.getReviewId())).thenReturn(review);

            sut.deductPointFromReviewDeleted(reviewDeletedCommand);

            verify(photoStore, times(1)).deleteAllByReview(review);
            verify(reviewStore, times(1)).delete(review);
        }
    }

    @Nested
    @DisplayName("ReviewModifiedTests")
    class ReviewModifiedTests {
        @Test
        void If_photo_is_deleted_from_review_point_is_deducted() {
            ReviewPointCommand nonPhotoReviewCommand = new ReviewPointCommand(
                    REVIEW_ID,
                    USER_ID,
                    PLACE_ID,
                    EMPTY_ATTACHED_PHOTO_LIST,
                    CONTENT);
            Review reviewToModify = new Review(REVIEW_ID, USER_ID, PLACE_ID);
            reviewToModify.addPhoto(new Photo("originPhoto", reviewToModify));
            Mockito.when(reviewReader.findReview(nonPhotoReviewCommand.getReviewId()))
                    .thenReturn(reviewToModify);

            sut.modifyPointFromReviewModified(nonPhotoReviewCommand);

            Mockito.verify(pointEventStore, times(1))
                    .saveReviewModifiedEvent(point, nonPhotoReviewCommand, Reason.DELETE_PHOTO);
        }

        @Test
        void If_photo_is_added_from_review_point_is_added() {
            ReviewPointCommand photoAddedCommand = new ReviewPointCommand(
                    REVIEW_ID,
                    USER_ID,
                    PLACE_ID,
                    List.of(PHOTO_ID),
                    CONTENT);
            Review reviewToModify = new Review(REVIEW_ID, USER_ID, PLACE_ID);
            Mockito.when(reviewReader.findReview(photoAddedCommand.getReviewId()))
                            .thenReturn(reviewToModify);

            sut.modifyPointFromReviewModified(photoAddedCommand);

            Mockito.verify(pointEventStore, times(1))
                    .saveReviewModifiedEvent(point, photoAddedCommand, Reason.ATTACH_PHOTO);
        }
    }
}