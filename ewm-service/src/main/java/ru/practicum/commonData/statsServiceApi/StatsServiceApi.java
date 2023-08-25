package ru.practicum.commonData.statsServiceApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatClient;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StatsServiceApi {
    private final StatClient statClient;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;
    @Value("${ewm.service.name}")
    private String appServiceName;

    public void saveStatistic(HttpServletRequest request) {
        EndpointHitDto hitDto = EndpointHitDto.builder()
                .app(appServiceName)
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        statClient.saveStats(hitDto);
    }

    public Long getViews(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(String.format("Event not found with id = %d", eventId)));
    if (event.getState() != State.PUBLISHED) {
        return 0L;
    }
        LocalDateTime start = event.getPublishedOn();
        LocalDateTime end = LocalDateTime.now();
        String uri = "/events/" + event.getId();
        List<ViewStatDto> statDto;
        try {
            ResponseEntity<Object> response = statClient.getStats(start, end, List.of(uri), true);
            String responseValue = objectMapper.writeValueAsString(response.getBody());
            statDto = Arrays.asList(objectMapper.readValue(responseValue, new TypeReference<>(){}));
        } catch (JsonProcessingException e) {
            throw new NotValidException("STATS SERVER ERROR: " + e.getMessage());
        }
        return statDto.isEmpty() ? 0 : statDto.get(0).getHits();
    }
}
