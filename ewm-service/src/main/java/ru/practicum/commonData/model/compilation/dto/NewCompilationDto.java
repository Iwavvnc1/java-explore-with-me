package ru.practicum.commonData.model.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    @NotNull
    private Set<Long> events;
    @NotNull
    private Boolean pinned; //Закреплена ли подборка на главной странице сайта
    @NotBlank
    private String title; //Заголовок подборки
}
