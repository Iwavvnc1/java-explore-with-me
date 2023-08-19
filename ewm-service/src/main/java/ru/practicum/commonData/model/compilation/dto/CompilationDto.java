package ru.practicum.commonData.model.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.event.dto.EventShortDto;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private Set<EventShortDto> events;
    private Long id;
    private Boolean pinned; //Закреплена ли подборка на главной странице сайта
    private String title; //Заголовок подборки
}
