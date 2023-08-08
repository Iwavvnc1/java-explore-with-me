package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatDto;

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
