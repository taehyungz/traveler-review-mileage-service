package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.PointEvent.Reason;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {
    private final ReviewReader reviewReader;
    private final ReviewStore reviewStore;
    private final PointReader pointReader;
    private final PointEventStore pointEventStore;
    private final PointEventReader pointEventReader;
    private final PhotoStore photoStore;

    @Override
    @Transactional
    public void earnsPointFromReviewAdded(ReviewPointCommand command) {
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        if (isContentHasText(command)) {
            pointEventStore.saveReviewAddedEvent(point, command, Reason.WRITE_REVIEW);
        }
        if (isPhotoAttachedToReview(command)) {
            pointEventStore.saveReviewAddedEvent(point, command, Reason.ATTACH_PHOTO);
        }
        if (isFirstReview(command)) {
            pointEventStore.saveReviewAddedEvent(point, command, Reason.FIRST_REVIEW);
        }

        Review savedReview = reviewStore.save(new Review(command.getReviewId(), command.getUserId(), command.getPlaceId()));
        photoStore.save(command.getAttachedPhotoIds(), savedReview);
    }

    @Override
    @Transactional
    public void deductPointFromReviewDeleted(ReviewPointCommand command) {
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        List<PointEvent> reviewDeletedPointEventList = getReviewDeletedPointEventList(command, point);
        pointEventStore.saveReviewDeletedEvent(reviewDeletedPointEventList);

        Review review = reviewReader.findReview(command.getReviewId());
        photoStore.deleteAllByReview(review);
        reviewStore.delete(review);
    }

    private List<PointEvent> getReviewDeletedPointEventList(ReviewPointCommand command, Point point) {
        return pointEventReader.findAllEventsByReviewId(command.getReviewId())
                .stream()
                .map(pointEvent -> PointEvent.deductPointEventFrom(point, pointEvent))
                .collect(Collectors.toList());
    }

    private boolean isPhotoAttachedToReview(ReviewPointCommand command) {
        return !command.getAttachedPhotoIds().isEmpty();
    }

    private boolean isContentHasText(ReviewPointCommand command) {
        return StringUtils.hasText(command.getContent());
    }

    private boolean isFirstReview(ReviewPointCommand command) {
        return !reviewReader.existsAnotherReviewInPlace(command.getPlaceId());
    }
}
