package ru.practicum.privateAccess.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.shtoto.CustomPageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.commonData.mapper.event.EventMapper.*;

@RequiredArgsConstructor
@Service
public class PrivateEventsServiceImpl implements PrivateEventsService {
    private final EventRepository eventRepository;
    ObjectMapper objectMapper;
    public EventDto createEvent(Long userId, NewEventDto eventDto) {
        eventDateValidation(eventDto.getEventDate());
        return toEventDtoFromEvent(eventRepository.save(toEventFromNewEventDto(eventDto)));
    }

    public List<EventShortDto> getEventsCurrentUser(Long userId, int from, int size) {
        return toEventShortDtoListFromListEvents(eventRepository
                .findAllByInitiatorId(userId, CustomPageRequest.of(from,size)).toList());
    }

    public EventDto getEventById(Long userId, Long eventId) {
        return toEventDtoFromEvent(eventRepository.findByIdIsAndInitiatorId(eventId,userId).orElseThrow());
    }

    public ParticipationRequestDto getEventRequestsById(Long userId, Long eventId) {
        return null;
    }

    @SneakyThrows
    public EventDto updateEventById(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
       Event event = eventRepository.findById(eventId).orElseThrow();
        eventDateValidation(event.getEventDate());
        objectMapper.updateValue(event,eventDto);
        return toEventDtoFromEvent(eventRepository.save(event));
    }

    public EventRequestStatusUpdateResult updateEventRequestStatusById(Long userId, Long eventId, EventRequestStatusUpdateRequest updateRequest) {
        return null;
    }

    private void eventDateValidation(LocalDateTime eventDate) {
        if(eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Field: eventDate. Error: the date and time for which the event is scheduled " +
                    "cannot be earlier than two hours from the current moment. Value: " + eventDate);
        }
    }
}
