package ru.practicum.privateAccess.service.event;

import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;

import java.util.List;

public interface PrivateEventsService {
    EventDto createEvent(Long userId, NewEventDto eventDto);

    List<EventShortDto> getEventsCurrentUser(Long userId, int from, int size);

    EventDto getEventById(Long userId, Long eventId);

    ParticipationRequestDto getEventRequestsById(Long userId, Long eventId);

    EventDto updateEventById(Long userId, Long eventId, UpdateEventUserRequest eventDto);

    EventRequestStatusUpdateResult updateEventRequestStatusById(Long userId, Long eventId,
                                                                EventRequestStatusUpdateRequest updateRequest);
}
