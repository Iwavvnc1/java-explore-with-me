package ru.practicum.commonData.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.request.dto.ConfirmedRequest;
import ru.practicum.commonData.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RequestManager {
    private final RequestRepository requestRepository;

    public List<EventDto> getEventDtosWithConfirmedRequest(List<EventDto> dtos) {
        List<Long> eventIds = new ArrayList<>();
        dtos.forEach(eventDto -> eventIds.add(eventDto.getId()));
        List<ConfirmedRequest> requests = requestRepository.findConfirmedRequests(eventIds);
        if (requests.isEmpty()) {
            dtos.forEach(eventDto -> {
                eventDto.setConfirmedRequests(0L);
            });
        } else {
            Map<Long, Long> confirmedRequests = requests.stream()
                    .collect(Collectors.toMap(ConfirmedRequest::getEventId, ConfirmedRequest::getCount));
            dtos.forEach(eventDto -> {
                eventDto.setConfirmedRequests(confirmedRequests.get(eventDto.getId()));
            });
        }
        return dtos;
    }

    public EventDto getEventDtoWithConfirmedRequest(EventDto eventDto) {
        ConfirmedRequest request = requestRepository.findConfirmedRequest(eventDto.getId())
                .orElse(new ConfirmedRequest(0L,0L));
            eventDto.setConfirmedRequests(request.getCount());
        return eventDto;
    }
}
