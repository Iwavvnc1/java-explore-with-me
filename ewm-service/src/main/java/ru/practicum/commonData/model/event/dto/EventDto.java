package ru.practicum.commonData.model.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.location.Location;
import ru.practicum.commonData.model.user.dto.UserDto;
import ru.practicum.commonData.model.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {
    private String annotation; //Краткое описание
    private CategoryDto category;
    private Integer confirmedRequests; //Количество одобренных заявок на участие в данном событии
    private LocalDateTime createdOn; //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description; //Полное описание события
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Long id;
    private UserDto initiator;
    private Location location; //Широта и долгота места проведения события
    private Boolean paid; //Нужно ли оплачивать участие
    private Integer participantLimit; //default: 0. Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    private LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private Boolean requestModeration; //default: true. Нужна ли пре-модерация заявок на участие
    private State state; //Список состояний жизненного цикла события
    private String title; //Заголовок
    private Integer views; //Количество просмотрев события
}
