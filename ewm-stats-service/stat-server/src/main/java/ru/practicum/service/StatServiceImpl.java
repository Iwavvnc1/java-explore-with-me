package ru.practicum.service;


import ru.practicum.dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.mapper.EndpointHitMapper.*;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public Object saveStatistic(EndpointHitDto endpointHit) {

        return toEndpointHitDto(statRepository.save(toEndpointHit(endpointHit)));
    }

    @Override
    public Object getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<EndpointHitDto> dtos = new ArrayList<>();
        if (unique) {
            List<EndpointHitDto> dtosWithoutHits = statRepository.getAllByTimeGroup(start, end, uris);
            dtosWithoutHits.forEach(dto -> {
                dto = dto.toBuilder().hits(statRepository.countAllGroup(start, end, dto.getUri())).build();
                dtos.add(dto);
            });
            return dtos;

        } else {
            List<EndpointHitDto> dtosWithoutHits = statRepository.getAllByTime(start, end, uris);
            dtosWithoutHits.forEach(dto -> {
                dto = dto.toBuilder().hits(statRepository.countAll(start, end, dto.getUri())).build();
                dtos.add(dto);
            });
            return dtos;
        }
    }
}
