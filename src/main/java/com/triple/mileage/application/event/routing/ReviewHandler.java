package com.triple.mileage.application.event.routing;

import com.triple.mileage.common.exception.InvalidParamException;
import com.triple.mileage.domain.point.PointService;
import com.triple.mileage.domain.point.dto.ReviewPointCommand;
import com.triple.mileage.interfaces.event.dto.EventRequest;
import com.triple.mileage.interfaces.event.dto.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewHandler implements EventTypeHandler {

    private final PointService pointService;

    @Override
    public boolean canSupport(EventType eventType) {
        return EventType.REVIEW == eventType;
    }

    @Override
    public void process(EventRequest eventRequest) {
        ReviewPointCommand command = ReviewPointCommand.from(eventRequest);
        switch (eventRequest.getAction()) {
            case ADD:
                pointService.earnsPointFromReviewAdded(command);
                break;
            case MOD:
                pointService.modifyPointFromReviewModified(command);
                break;
            case DELETE:
                pointService.deductPointFromReviewDeleted(command);
                break;
            default:
                throw new InvalidParamException();
        }
    }
}
