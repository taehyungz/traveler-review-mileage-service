package com.triple.mileage.interfaces.event.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@ToString
public class EventRequest {
    @NotNull
    private EventType type;

    @NotNull
    private ActionType action;

    @NotEmpty
    private String reviewId;

    private String content;

    private List<String> attachedPhotoIds;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String placeId;
}
