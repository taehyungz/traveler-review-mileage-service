package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {
    private final ReviewReader reviewReader;
    private final ReviewStore reviewStore;
    private final PointReader pointReader;
    private final PointEventStore pointEventStore;
    private final PhotoStore photoStore;

    @Override
    @Transactional
    public void earnsPointFromReviewAdded(ReviewPointCommand command) {
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        if (isContentHasText(command)) {
            pointEventStore.saveReviewWrittenEvent(point, command);
        }
        if (isPhotoAttachedToReview(command)) {
            pointEventStore.savePhotoAttachedEvent(point, command);
        }
        if (isFirstReview(command)) {
            pointEventStore.saveFirstReviewEvent(point, command);
        }

        Review savedReview = reviewStore.save(command);
        photoStore.save(command.getAttachedPhotoIds(), savedReview);
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
