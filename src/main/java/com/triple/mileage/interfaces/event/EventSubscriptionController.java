package com.triple.mileage.interfaces.event;

import com.triple.mileage.application.event.EventSubscriptionFacade;
import com.triple.mileage.common.interceptor.RequestUUIDLoggingInterceptor;
import com.triple.mileage.common.response.CommonResponse;
import com.triple.mileage.interfaces.event.dto.EventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventSubscriptionController {

    private final EventSubscriptionFacade eventSubscriptionFacade;

    @PostMapping("/events")
    public CommonResponse subscribeEvent(@RequestBody @Valid EventRequest eventRequest) {
        final String requestId = MDC.get(RequestUUIDLoggingInterceptor.TRACE_ID);
        log.info("requestId = ({}), EVNT:SUBS:EventSubscriptionController.subscribeEvent request = ({})",
                requestId, eventRequest);
        eventSubscriptionFacade.process(eventRequest);
        return CommonResponse.success(null);
    }
}
