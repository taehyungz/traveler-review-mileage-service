package com.triple.mileage.application.event.routing;

import com.triple.mileage.interfaces.event.dto.EventRequest;
import com.triple.mileage.interfaces.event.dto.EventType;

public interface EventTypeHandler {
    boolean canSupport(EventType eventType);
    
    void process(EventRequest eventRequest);
}
