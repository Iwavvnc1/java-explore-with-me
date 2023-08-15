package ru.practicum.privateAccess.service.request;

import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;

public interface PrivateRequestService {
    ParticipationRequestDto getRequests(Long userId);

    ParticipationRequestDto addRequest(Long userId, Long eventId);
}
