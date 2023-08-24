package ru.practicum.commonData.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.location.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation; //Краткое описание
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description; //Полное описание события
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Location location; //Широта и долгота места проведения события
    private Boolean paid; //Нужно ли оплачивать участие
    private Integer participantLimit; //default: 0. Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    private Boolean requestModeration; //default: true. Нужна ли пре-модерация за
    @NotBlank
    @Size(min = 3, max = 120)
    private String title; //Заголовок
}