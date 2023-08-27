package ru.practicum.commonData.utils.statsServiceApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class StatsServiceApi {
    private final StatClient statClient;
    private final ObjectMapper objectMapper;
    @Value("${ewm.service.name}")
    private String appServiceName;
    private static final TypeReference<List<ViewStatDto>> TYPE_REFERENCE_LIST = new TypeReference<>() {};


    public void saveStatistic(HttpServletRequest request) {
        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(appServiceName)
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        statClient.saveStats(hitDto);
    }

    public EventDto getEventDtoWithViews(EventDto eventDto) {
    if (eventDto.getState() != State.PUBLISHED) {
        eventDto.setViews(0L);
        return eventDto;
    }
        LocalDateTime start = eventDto.getPublishedOn();
        LocalDateTime end = LocalDateTime.now();
        String uri = "/events/" + eventDto.getId();
        List<ViewStatDto> statDto;
        try {
            ResponseEntity<Object> response = statClient.getStats(start, end, List.of(uri), true);
            String responseValue = objectMapper.writeValueAsString(response.getBody());
            statDto = Arrays.asList(objectMapper.readValue(responseValue, new TypeReference<>(){}));
        } catch (JsonProcessingException e) {
            throw new NotValidException("STATS SERVER ERROR: " + e.getMessage());
        }
        if (statDto.isEmpty()) {
            eventDto.setViews(0L);
        } else {
            eventDto.setViews(statDto.get(0).getHits());
        }
        return eventDto;
    }

    public List<EventDto> getEventDtosWithViews(List<EventDto> events) {
        LocalDateTime start = events.get(0).getCreatedOn();
        LocalDateTime end = LocalDateTime.now();
        List<String> uris = new ArrayList<>();
        String uri = "";
        Map<String, EventDto> eventsUri = new HashMap<>();
        for (EventDto event : events) {
            if (start.isBefore(event.getCreatedOn())) {
                start = event.getCreatedOn();
            }
            uri = "/events/" + event.getId();
            uris.add(uri);
            eventsUri.put(uri, event);
            event.setViews(0L);
        }
        ResponseEntity<Object> response = statClient.getStats(start, end, uris, true);
        if (response.getStatusCode() == HttpStatus.OK) {
            List<ViewStatDto> stats = objectMapper.convertValue(response.getBody(), TYPE_REFERENCE_LIST);
            stats.forEach((stat) ->
                    eventsUri.get(stat.getUri()).setViews(stat.getHits()));
        }
        return new ArrayList<>(eventsUri.values());
    }
}
