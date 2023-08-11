package ru.practicum.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.mapper.EndpointHitMapper.*;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public EndpointHitDto saveStatistic(EndpointHitDto endpointHit) {
        return toEndpointHitDto(statRepository.save(toEndpointHit(endpointHit)));
    }

    @Override
    public List<ViewStats> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStats> viewStats;
        if (unique) {
            viewStats = statRepository.getAllByTimeGroup(start, end, uris);
        } else {
            viewStats = statRepository.getAllByTime(start, end, uris);
        }
        return viewStats;
    }
}
