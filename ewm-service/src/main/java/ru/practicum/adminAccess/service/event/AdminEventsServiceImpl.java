package ru.practicum.adminAccess.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.enums.AdminStateAction;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.UpdateEventAdmin;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.shtoto.CustomPageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.commonData.mapper.event.EventMapper.*;

@RequiredArgsConstructor
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<EventDto> getEvents(AdminEventsParam requestParam) {
        CustomPageRequest pageRequest = new CustomPageRequest(requestParam.getFrom(), requestParam.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));
        List<Event> events = eventRepository.findAllByParams(requestParam.getUsers(), requestParam.getStates(),
                        requestParam.getCategories(), requestParam.getRangeStart(), requestParam.getRangeEnd(), pageRequest)
                .toList();
        return toEventDtoListFromListEvents(events);
    }

    @Transactional
    @SneakyThrows
    @Override
    public EventDto updateEvent(Long eventId, UpdateEventAdmin eventDto) {
        Event eventOld = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event not found with id = %d", eventId)));
        if (eventOld.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ConflictException("Field: eventDate. Error: the date and time for which the event is scheduled" +
                    " cannot be earlier than two hours from the current moment. Value: " + eventOld.getEventDate());
        }
        if (eventOld.getState().equals(State.PUBLISHED) || eventOld.getState().equals(State.CANCELED)) {
            throw new ConflictException(String
                    .format("Cannot publish the event because it's not in the right state: %s", eventOld.getState()));
        } else {
            if (eventDto.getStateAction().toString().equals(AdminStateAction.PUBLISH_EVENT.toString())) {
                eventOld.setState(State.PUBLISHED);
            }
            if (eventDto.getStateAction().toString().equals(AdminStateAction.REJECT_EVENT.toString())) {
                eventOld.setState(State.CANCELED);
            }
        }
        Event eventUpdate = toEventFromUpdateEventAdmin(eventDto);
        eventOld = objectMapper.updateValue(eventOld, eventUpdate);
        return toEventDtoFromEvent(eventOld);
    }
}
