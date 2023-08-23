package ru.practicum.adminAccess.service.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.enums.AdminStateAction;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.UpdateEventAdmin;
import ru.practicum.commonData.repository.CategoryRepository;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.customPageRequest.CustomPageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.commonData.mapper.event.EventMapper.*;

@RequiredArgsConstructor
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventDto> getEvents(AdminEventsParam requestParam) {
        CustomPageRequest pageRequest = new CustomPageRequest(requestParam.getFrom(), requestParam.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));
        Page<Event> events = eventRepository.findAllByParams(requestParam.getUsers(), requestParam.getStates(),
                        requestParam.getCategories(), requestParam.getRangeStart(), requestParam.getRangeEnd(),
                pageRequest);
        return toEventDtoListFromListEvents(events.toList());
    }

    @Transactional
    @SneakyThrows
    @Override
    public EventDto updateEvent(Long eventId, UpdateEventAdmin eventDto) {
        Event eventOld = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event not found with id = %d", eventId)));
        if (eventOld.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new NotValidException("Field: eventDate. Error: the date and time for which the event is scheduled" +
                    " cannot be earlier than one hour from the current moment. Value: " + eventOld.getEventDate());
        }
        if (eventDto.getEventDate() != null && eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new NotValidException("Field: eventDate. Error: the date and time for which the event is scheduled" +
                    " cannot be earlier than one hour from the current moment. Value: " + eventDto.getEventDate());
        }
        if (eventDto.getCategory() != null) {
            eventOld.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                            eventDto.getCategory()))));
        }
        if (eventOld.getState().equals(State.PUBLISHED) || eventOld.getState().equals(State.CANCELED)) {
            throw new ConflictException(String
                    .format("Cannot publish the event because it's not in the right state: %s", eventOld.getState()));
        } else {
            if (eventDto.getStateAction() != null && eventDto.getStateAction().toString()
                    .equals(AdminStateAction.PUBLISH_EVENT.toString())) {
                eventOld.setState(State.PUBLISHED);
                eventOld.setPublishedOn(LocalDateTime.now());
            }
            if (eventDto.getStateAction() != null && eventDto.getStateAction()
                    .toString().equals(AdminStateAction.REJECT_EVENT.toString())) {
                eventOld.setState(State.CANCELED);
            }
        }

        Event updateEvent = updateEventFromUpdateEventAdmin(eventDto,eventOld);
        return toEventDtoFromEvent(updateEvent);
    }
}
