package ru.practicum.commonData.model.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.location.Location;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEventUserRequest {
    private String annotation; //Краткое описание
    private CategoryDto category;
    private String description; //Полное описание события
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Location location; //Широта и долгота места проведения события
    private Boolean paid; //Нужно ли оплачивать участие
    private Integer participantLimit; //default: 0. Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    private Boolean requestModeration; //default: true. Нужна ли пре-модерация заявок на участие
    private State state; //Список состояний жизненного цикла события
    private String title; //Заголовок
}
