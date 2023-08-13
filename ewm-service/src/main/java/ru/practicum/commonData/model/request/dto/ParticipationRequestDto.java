package ru.practicum.commonData.model.request.dto;

import ru.practicum.commonData.enums.State;

import java.time.LocalDateTime;

public class ParticipationRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private State status;
}
