package ru.practicum.mapper;

import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DtoMapper {
    public static ViewStatDto toViewStatDto(EndpointHitDto endpointHitDto, Long hits) {
        return ViewStatDto.builder()
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .hits(hits)
                .build();
    }

}
