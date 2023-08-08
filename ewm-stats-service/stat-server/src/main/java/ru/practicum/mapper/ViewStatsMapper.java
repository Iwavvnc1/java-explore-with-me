package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.ViewStatDto;
import ru.practicum.model.ViewStats;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ViewStatsMapper {

    public static ViewStats toViewStats(ViewStatDto dto) {
        return ViewStats.builder()
                .app(dto.getApp())
                .uri(dto.getUri())
                .hits(dto.getHits())
                .build();
    }

    public static ViewStatDto toDto(ViewStats viewStats) {
        return ViewStatDto.builder()
                .app(viewStats.getApp())
                .uri(viewStats.getUri())
                .hits(viewStats.getHits())
                .build();
    }

    public static List<ViewStatDto> toDtoList(List<ViewStats> viewStats) {
        return viewStats.stream().map(ViewStatsMapper::toDto).collect(Collectors.toList());
    }
}
