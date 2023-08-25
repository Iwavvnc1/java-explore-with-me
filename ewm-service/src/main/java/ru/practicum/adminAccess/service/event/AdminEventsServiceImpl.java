package ru.practicum.adminAccess.service.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.enums.AdminStateAction;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.UpdateEventAdmin;
import ru.practicum.commonData.model.request.dto.ConfirmedRequest;
import ru.practicum.commonData.repository.CategoryRepository;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.customPageRequest.CustomPageRequest;
import ru.practicum.commonData.repository.RequestRepository;
import ru.practicum.commonData.statsServiceApi.StatsServiceApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ru.practicum.commonData.enums.State.*;
import static ru.practicum.commonData.mapper.event.EventMapper.*;

@RequiredArgsConstructor
@Service
public class AdminEventsServiceImpl implements AdminEventsService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final StatsServiceApi statsService;

    @Override
    public List<EventDto> getEvents(AdminEventsParam requestParam) {
        CustomPageRequest pageRequest = new CustomPageRequest(requestParam.getFrom(), requestParam.getSize(),
                Sort.by(Sort.Direction.ASC, "id"));
        Page<Event> events = eventRepository.findAllByParams(requestParam.getUsers(), requestParam.getStates(),
                        requestParam.getCategories(), requestParam.getRangeStart(), requestParam.getRangeEnd(),
                pageRequest);
        List<EventDto> result = toEventDtoListFromListEvents(events.toList());
        List<Long> eventIds = new ArrayList<>();
        result.forEach(eventDto -> eventIds.add(eventDto.getId()));
        List<ConfirmedRequest> requests = requestRepository.findConfirmedRequest(eventIds);
        HashMap<Long,Long> confirmedRequests = new HashMap<>();
        requests.forEach(confirmedRequest -> confirmedRequests
                .put(confirmedRequest.getEventId(), confirmedRequest.getCount()));
        if (requests.isEmpty()) {
            result.forEach(eventDto -> {
                eventDto.setConfirmedRequests(0L);
                eventDto.setViews(statsService.getViews(eventDto.getId()));
            });
        } else {
            result.forEach(eventDto -> {
                eventDto.setConfirmedRequests(confirmedRequests.get(eventDto.getId()));
                eventDto.setViews(statsService.getViews(eventDto.getId()));
            });
        }
        return result;
    }

    @Transactional
    @SneakyThrows
    @Override
    public EventDto updateEvent(Long eventId, UpdateEventAdmin eventDto) {
        Event eventOld = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event not found with id = %d", eventId)));
        if (eventDto.getEventDate() != null && eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new NotValidException("Field: eventDate. Error: the date and time for which the event is scheduled" +
                    " cannot be earlier than one hour from the current moment. Value: " + eventDto.getEventDate());
        }
        if (eventDto.getCategory() != null) {
            eventOld.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                            eventDto.getCategory()))));
        }
        if (eventOld.getState().equals(PUBLISHED) || eventOld.getState().equals(CANCELED)) {
            throw new ConflictException(String
                    .format("Cannot publish the event because it's not in the right state: %s", eventOld.getState()));
        } else {
            if (eventDto.getStateAction() != null && eventDto.getStateAction().toString()
                    .equals(AdminStateAction.PUBLISH_EVENT.toString())) {
                eventOld.setState(PUBLISHED);
                eventOld.setPublishedOn(LocalDateTime.now());
            }
            if (eventDto.getStateAction() != null && eventDto.getStateAction()
                    .toString().equals(AdminStateAction.REJECT_EVENT.toString())) {
                eventOld.setState(CANCELED);
            }
        }
        Event updateEvent = updateEventFromUpdateEventRequest(eventDto,eventOld);
        return toEventDtoFromEvent(updateEvent);
    }
}
