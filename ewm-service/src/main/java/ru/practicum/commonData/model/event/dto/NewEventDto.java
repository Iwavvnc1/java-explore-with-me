package ru.practicum.commonData.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.location.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank
    private String annotation; //Краткое описание
    @NotNull
    private Long category;
    @NotBlank
    private String description; //Полное описание события
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private Location location; //Широта и долгота места проведения события
    @NotNull
    private Boolean paid; //Нужно ли оплачивать участие
    @NotNull
    private Integer participantLimit; //default: 0. Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    @NotNull
    private Boolean requestModeration; //default: true. Нужна ли пре-модерация заявок на участие
    @NotBlank
    private String title; //Заголовок
}
