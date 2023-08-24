package ru.practicum.privateAccess.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.mapper.request.RequestMapper;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.request.ParticipationRequest;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.commonData.model.user.User;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.RequestRepository;
import ru.practicum.commonData.repository.UserRepository;

import java.util.List;

import static ru.practicum.commonData.mapper.request.RequestMapper.*;

@RequiredArgsConstructor
@Service
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId) {
        if (userRepository.existsById(userId)) {
            return RequestMapper.toParticipationRequestDtoList(requestRepository.findAllByRequesterId(userId));
        } else {
            throw new NotFoundException(String.format("User not found with id = %s", userId));
        }
    }

    @Transactional
    @Override
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User not found with id = %s", userId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event not found with id = %s", eventId)));
        if (requestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            throw new ConflictException(String.format("Request with requesterId=%d and eventId=%d already exist", userId, eventId));
        }
        if (userId.equals(event.getInitiator().getId())) {
            throw new ConflictException(String.format("User with id=%d must not be equal to initiator", userId));
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format("Event with id=%d is not published", eventId));
        }
        if (event.getParticipantLimit() != 0 && (event.getParticipantLimit().equals(event.getConfirmedRequests()))) {
            throw new ConflictException(String.format("Event with id=%d has reached participant limit", eventId));
        }
        if (!event.getRequestModeration()) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        ParticipationRequest participationRequest;
        try {
            participationRequest = requestRepository.save(toParticipationRequest(user, event));
            requestRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        return toParticipationRequestDto(participationRequest);
    }

    @Transactional
    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d " +
                        "and requesterId=%d was not found", requestId, userId)));
        request.setStatus(Status.CANCELED);
        try {
            request = requestRepository.save(request);
            requestRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        return toParticipationRequestDto(request);
    }
}
