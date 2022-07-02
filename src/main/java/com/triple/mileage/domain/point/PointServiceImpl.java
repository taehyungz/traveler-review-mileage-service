package com.triple.mileage.domain.point;

import com.triple.mileage.common.exception.IllegalStatusException;
import com.triple.mileage.common.interceptor.RequestUUIDLoggingInterceptor;
import com.triple.mileage.domain.photo.PhotoStore;
import com.triple.mileage.domain.point.PointEvent.Reason;
import com.triple.mileage.domain.point.dto.PointEventInfo;
import com.triple.mileage.domain.point.dto.PointInfo;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.domain.review.Review;
import com.triple.mileage.domain.review.ReviewReader;
import com.triple.mileage.domain.review.ReviewStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
        final String requestId = MDC.get(RequestUUIDLoggingInterceptor.TRACE_ID);
        log.info("requestId = ({}), EVNT:SUBS:PointServiceImpl.earnsPointFromReviewAdded command = ({})",
                requestId, command);
        validateReviewAddedCommand(command);
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        if (hasText(command)) {
            pointEventStore.saveReviewAddedEvent(point, command, Reason.WRITE_TEXT);
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
        final String requestId = MDC.get(RequestUUIDLoggingInterceptor.TRACE_ID);
        log.info("requestId = ({}), EVNT:SUBS:PointServiceImpl.deductPointFromReviewDeleted command = ({})",
                requestId, command);
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        List<PointEvent> reviewDeletedPointEventList = getReviewDeletedPointEventList(command, point);
        pointEventStore.saveReviewDeletedEvent(reviewDeletedPointEventList);

        Review reviewToDelete = reviewReader.findReview(command.getReviewId());
        photoStore.deleteAllByReview(reviewToDelete);
        reviewStore.delete(reviewToDelete);
    }

    @Override
    @Transactional
    public void modifyPointFromReviewModified(ReviewPointCommand command) {
        final String requestId = MDC.get(RequestUUIDLoggingInterceptor.TRACE_ID);
        log.info("requestId = ({}), EVNT:SUBS:PointServiceImpl.modifyPointFromReviewModified command = ({})",
                requestId, command);
        Point point = pointReader.findByUser(command.getUserId());
        point.versionUp();

        Review reviewToModify = reviewReader.findReview(command.getReviewId());

        if (reviewToModify.getPhotoList().isEmpty() && !command.getAttachedPhotoIds().isEmpty()) {
            pointEventStore.saveReviewModifiedEvent(point, command, Reason.ATTACH_PHOTO);
        }
        if (!reviewToModify.getPhotoList().isEmpty() && command.getAttachedPhotoIds().isEmpty()) {
            pointEventStore.saveReviewModifiedEvent(point, command, Reason.DELETE_PHOTO);
        }
    }

    @Override
    public int getUserPoint(String userId) {
        return pointReader.findByUser(userId)
                .getAmount();
    }

    @Override
    public PointInfo getUserPointWithEvents(String userId) {
        Point userPoint = pointReader.findByUser(userId);
        List<PointEventInfo> pointEventInfoList = pointEventReader.findAllEventsByUserId(userId);
        return new PointInfo(userPoint.getAmount(), pointEventInfoList);
    }

    private void validateReviewAddedCommand(ReviewPointCommand command) {
        boolean isWrittenReviewOnSamePlace = reviewReader
                .isAlreadyWittenReviewInPlace(command.getUserId(), command.getPlaceId());
        if (isWrittenReviewOnSamePlace) {
            throw new IllegalStatusException("회원이 이미 같은 장소에 리뷰를 작성했습니다");
        }
    }

    private List<PointEvent> getReviewDeletedPointEventList(ReviewPointCommand command, Point point) {
        return pointEventReader.findAllEventsByReviewId(command.getReviewId())
                .stream()
                .map(pointEvent -> PointEvent.deductPointEventFrom(point, pointEvent, PointEvent.ActionType.DELETE))
                .collect(Collectors.toList());
    }

    private boolean isPhotoAttachedToReview(ReviewPointCommand command) {
        return !command.getAttachedPhotoIds().isEmpty();
    }

    private boolean hasText(ReviewPointCommand command) {
        return StringUtils.hasText(command.getContent());
    }

    private boolean isFirstReview(ReviewPointCommand command) {
        return !reviewReader.existsAnotherReviewInPlace(command.getPlaceId());
    }
}
