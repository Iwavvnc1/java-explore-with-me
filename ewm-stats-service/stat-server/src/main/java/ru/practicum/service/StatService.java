package ru.practicum.service;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    EndpointHitDto saveStatistic(EndpointHitDto endpointHit);

    List<ViewStatDto> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
