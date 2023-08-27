package ru.practicum.privateAccess.service.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.model.request.dto.ConfirmedRequest;
import ru.practicum.commonData.utils.RequestManager;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.enums.StateAction;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.mapper.event.EventMapper;
import ru.practicum.commonData.mapper.request.RequestMapper;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.ParticipationRequest;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.commonData.repository.CategoryRepository;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.RequestRepository;
import ru.practicum.commonData.repository.UserRepository;
import ru.practicum.commonData.utils.customPageRequest.CustomPageRequest;
import ru.practicum.commonData.utils.statsServiceApi.StatsServiceApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.commonData.enums.Status.CONFIRMED;
import static ru.practicum.commonData.enums.Status.REJECTED;
import static ru.practicum.commonData.mapper.event.EventMapper.*;
import static ru.practicum.commonData.mapper.request.RequestMapper.*;

@RequiredArgsConstructor
@Service
public class PrivateEventsServiceImpl implements PrivateEventsService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final RequestManager requestManager;
    private final StatsServiceApi statsService;

    @Transactional
    @Override
    public EventDto createEvent(Long userId, NewEventDto eventDto) {
        eventDateValidation(eventDto.getEventDate());
        Event event = EventMapper.toEventFromNewEventDto(eventDto);
        event.setCategory(categoryRepository.findById(eventDto.getCategory())
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                        eventDto.getCategory()))));
        event.setInitiator(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id=%d was not found", userId))));
        event.setCreatedOn(LocalDateTime.now());
        event.setState(State.PENDING);
        try {
            eventRepository.save(event);
            eventRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(), e);
        }
        return toEventDtoFromEvent(event);
    }

    public List<EventShortDto> getEventsCurrentUser(Long userId, int from, int size) {
        List<EventDto> eventDtos = eventRepository
                .findAllByInitiatorId(userId, CustomPageRequest.of(from, size)).stream()
                .map(EventMapper::toEventDtoFromEvent)
                .collect(Collectors.toList());
        List<Long> eventIds = requestManager.getIdsForRequest(eventDtos);
        List<ConfirmedRequest> requests = requestRepository.findConfirmedRequests(eventIds);
        eventDtos = requestManager
                .getEventDtosWithConfirmedRequest(statsService.getEventDtosWithViews(eventDtos),requests);
        return toEventShortDtoListFromListEventDtos(eventDtos);

    }

    @Override
    public EventDto getEventById(Long userId, Long eventId) {
        EventDto eventDto = toEventDtoFromEvent(eventRepository.findByIdIsAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event not found with id = %d and userId = %d", eventId, userId))));
        ConfirmedRequest request = requestRepository.findConfirmedRequest(eventDto.getId())
                .orElse(new ConfirmedRequest(0L,0L));
        return requestManager.getEventDtoWithConfirmedRequest(statsService.getEventDtoWithViews(eventDto),request);
    }

    @Override
    public List<ParticipationRequestDto> getEventRequestsById(Long userId, Long eventId) {
        if (eventRepository.findByIdIsAndInitiatorId(eventId, userId).isEmpty()) {
            throw new NotFoundException(
                    String.format("Event not found with id = %d and userId = %d", eventId, userId));
        }
        return toParticipationRequestDtoList(requestRepository.findAllByEventId(eventId));
    }

    @Transactional
    @Override
    @SneakyThrows
    public EventDto updateEventById(Long userId, Long eventId, UpdateEventUser eventDto) {
        Event oldEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event not found with id = %d and userId = %d", eventId, userId)));
        eventDateValidation(oldEvent.getEventDate());
        eventDateValidation(eventDto.getEventDate());
        if (oldEvent.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Event must not be published");
        }
        if (eventDto.getCategory() != null) {
            oldEvent.setCategory(categoryRepository.findById(eventDto.getCategory())
                    .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found",
                            eventDto.getCategory()))));
        }
        if (eventDto.getStateAction() != null && StateAction.CANCEL_REVIEW.toString()
                .equals(eventDto.getStateAction().toString())) {
            oldEvent.setState(State.CANCELED);
        } else if (eventDto.getStateAction() != null && StateAction.SEND_TO_REVIEW.toString()
                .equals(eventDto.getStateAction().toString())) {
            oldEvent.setState(State.PENDING);
        }
        Event updateEvent = updateEventFromUpdateEventRequest(eventDto,oldEvent);
        try {
            updateEvent = eventRepository.save(updateEvent);
            eventRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        return toEventDtoFromEvent(updateEvent);
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatusById(Long userId, Long eventId,
                                                                       EventRequestStatusUpdateRequest request) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format("Event not found with id = %d and userId = %d", eventId, userId)));

        List<ParticipationRequest> confirmedRequests = List.of();
        List<ParticipationRequest> rejectedRequests = List.of();

        Set<Long> requestIds = request.getRequestIds();
        List<ParticipationRequest> requests = requestRepository.findAllByIdIn(requestIds);

        String status = request.getStatus().toString();

        if (status.equals(REJECTED.toString())) {
            if (status.equals(REJECTED.toString())) {
                boolean existConfirmedRequest = requests.stream()
                        .anyMatch(reques -> reques.getStatus().equals(CONFIRMED));
                if (existConfirmedRequest) {
                    throw new ConflictException("Cannot reject confirmed requests");
                }
                rejectedRequests = requests;
                rejectedRequests.forEach(reques -> reques.setStatus(REJECTED));
                return new EventRequestStatusUpdateResult(new ArrayList<>(), rejectedRequests.stream()
                        .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList()));
            }
        }
        Integer participantLimit = event.getParticipantLimit();
        Integer approvedRequests = requestRepository.countAllByEventIdAndStatus(eventId, CONFIRMED);
        int availableParticipants = participantLimit - approvedRequests;
        int potentialParticipants = requestIds.size();

        if (participantLimit > 0 && participantLimit.equals(approvedRequests)) {
            throw new ConflictException(String.format("Event with id=%d has reached participant limit", eventId));
        }
        if (status.equals(CONFIRMED.toString())) {
            if (participantLimit.equals(0) ||
                    (potentialParticipants <= availableParticipants && !event.getRequestModeration())) {
                confirmedRequests = requests;
                confirmedRequests.forEach(reques -> {
                    if (!reques.getStatus().equals(CONFIRMED)) {
                        reques.setStatus(CONFIRMED);
                    } else {
                        throw new ConflictException(String.format("Request with id=%d has already been confirmed",
                                reques.getId()));
                    }
                });
            } else {
                confirmedRequests = requests.stream()
                        .limit(availableParticipants)
                        .collect(Collectors.toList());
                confirmedRequests.forEach(reques -> {
                    if (!reques.getStatus().equals(CONFIRMED)) {
                        reques.setStatus(CONFIRMED);
                    }
                });
                rejectedRequests = requests.stream()
                        .skip(availableParticipants)
                        .collect(Collectors.toList());
                rejectedRequests.forEach(reques -> {
                    if (!reques.getStatus().equals(REJECTED)) {
                        reques.setStatus(REJECTED);
                    }
                });
            }
        }
        try {
            requestRepository.saveAll(confirmedRequests);
            eventRepository.save(event);
            requestRepository.saveAll(rejectedRequests);
            requestRepository.flush();
            eventRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        List<ParticipationRequestDto> confirmedRequestDto = confirmedRequests.stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
        List<ParticipationRequestDto> rejectedRequestsDto = rejectedRequests.stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
        return new EventRequestStatusUpdateResult(confirmedRequestDto, rejectedRequestsDto);
    }

    private void eventDateValidation(LocalDateTime eventDate) {
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new NotValidException("Field: eventDate. Error: the date and time for which the event is scheduled " +
                    "cannot be earlier than two hours from the current moment. Value: " + eventDate);
        }
    }
}
