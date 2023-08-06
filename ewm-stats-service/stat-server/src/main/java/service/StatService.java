package service;

import dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    Object saveStatistic(EndpointHitDto endpointHit);

    Object getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
