package service;


import dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import repository.StatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public Object saveStatistic(EndpointHitDto endpointHit) {
        return statRepository.save(endpointHit);
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
