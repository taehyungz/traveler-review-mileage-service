package com.triple.mileage.domain.point;

import com.triple.mileage.domain.photo.Photo;
import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService {
    private final ReviewReader reviewReader;
    private final ReviewStore reviewStore;
    private final PointReader pointReader;
    private final PointStore pointStore;
    private final PointEventStore pointEventStore;
    private final PhotoStore photoStore;

    private static final int ONE_POINT = 1;

    @Override
    @Transactional
    public void earnsPointFromReviewAdded(ReviewPointCommand command) {
        List<Review> placeList = reviewReader.findByPlace(command.getPlaceId());
        final boolean isFirstReview = placeList.isEmpty();

        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        if (StringUtils.hasText(command.getContent())) {
            PointEvent pointEvent = new PointEvent(
                    command.getReviewId(),
                    command.getUserId(),
                    PointEvent.Reason.WRITE_REVIEW,
                    ONE_POINT,
                    point.getVersion(),
                    point);
            pointEventStore.save(pointEvent);
            point.pointUp();
        }
        if (!command.getAttachedPhotoIds().isEmpty()) {
            PointEvent pointEvent = new PointEvent(
                    command.getReviewId(),
                    command.getUserId(),
                    PointEvent.Reason.ATTACH_PHOTO,
                    ONE_POINT,
                    point.getVersion(),
                    point);
            pointEventStore.save(pointEvent);
            point.pointUp();
        }
        if (isFirstReview) {
            PointEvent pointEvent = new PointEvent(
                    command.getReviewId(),
                    command.getUserId(),
                    PointEvent.Reason.FIRST_REVIEW,
                    ONE_POINT,
                    point.getVersion(),
                    point);
            pointEventStore.save(pointEvent);
            point.pointUp();
        }
        pointStore.save(point);

        Review initReview = new Review(
                command.getReviewId(),
                command.getUserId(),
                command.getPlaceId());
        Review savedReview = reviewStore.save(initReview);
        for (String photoId: command.getAttachedPhotoIds()) {
            Photo photo = new Photo(photoId, savedReview);
            savedReview.addPhoto(photo);
            photoStore.save(photo);
        }
    }
}
