package ru.practicum.commonData.model.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    private Set<Long> events;
    private Boolean pinned; //Закреплена ли подборка на главной странице сайта
    @Size(min = 1,max = 50)
    @NotBlank
    private String title; //Заголовок подборки
}
