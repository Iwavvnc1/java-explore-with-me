package ru.practicum.privateAccess.service;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;

import java.util.List;

@Service
public class PrivateEventsService {
    public EventDto createEvent(Long userId, NewEventDto eventDto) {
        return null;
    }

    public List<EventDto> getEventsCurrentUser(Long userId, int from, int size) {
        return null;
    }

    public EventDto getEventById(Long userId, Long eventId) {
        return null;
    }

    public ParticipationRequestDto getEventRequestsById(Long userId, Long eventId) {
        return null;
    }

    public EventDto updateEventById(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
        return null;
    }

    public EventRequestStatusUpdateResult updateEventRequestStatusById(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        return null;
    }
}
