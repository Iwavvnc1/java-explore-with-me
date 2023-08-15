package ru.practicum.privateAccess.service.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.commonData.model.user.User;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.RequestRepository;
import ru.practicum.commonData.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class PrivateRequestServiceImpl implements PrivateRequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public ParticipationRequestDto getRequests(Long userId) {
        return null;
    }

    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();

        return null;
    }
}
