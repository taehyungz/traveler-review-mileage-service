package com.triple.mileage.interfaces.event;

import com.triple.mileage.application.event.EventSubscriptionFacade;
import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventSubscriptionController {

    private final EventSubscriptionFacade eventSubscriptionFacade;

    @PostMapping("/events")
    public void subscribeEvent(@RequestBody EventRequest eventRequest) {
        log.info("EVNT:SUBS:EventSubscriptionController.subscribeEvent request: ({})", eventRequest);
        eventSubscriptionFacade.process(eventRequest);
    }
}
