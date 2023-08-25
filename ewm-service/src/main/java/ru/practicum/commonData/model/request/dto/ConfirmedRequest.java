package ru.practicum.commonData.model.request.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class ConfirmedRequest {
    Long count;
    Long eventId;

    public ConfirmedRequest(Long eventId, Long count) {
        this.count = count;
        this.eventId = eventId;
    }
}
