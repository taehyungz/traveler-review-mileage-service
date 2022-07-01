package com.triple.mileage.application.event;

import com.triple.mileage.application.event.routing.EventTypeHandler;
import com.triple.mileage.common.exception.InvalidParamException;
import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventSubscriptionFacade {

    private final List<EventTypeHandler> eventTypeHandlerList;

    public void process(EventRequest eventRequest) {
        EventTypeHandler matchedHandler = routingHandler(eventRequest);
        matchedHandler.process(eventRequest);
    }

    private EventTypeHandler routingHandler(EventRequest eventRequest) {
        return eventTypeHandlerList.stream()
                .filter(eventTypeHandler -> eventTypeHandler.canSupport(eventRequest.getType()))
                .findFirst()
                .orElseThrow(() -> new InvalidParamException("존재하지 않는 이벤트 타입입니다"));
    }
}
