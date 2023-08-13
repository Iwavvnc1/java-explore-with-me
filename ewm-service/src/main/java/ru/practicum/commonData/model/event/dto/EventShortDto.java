package ru.practicum.commonData.model.event.dto;

import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.user.dto.UserShortDto;

import java.time.LocalDateTime;

public class EventShortDto {
    private String annotation; //Краткое описание
    private CategoryDto category;
    private Integer confirmedRequests; //Количество одобренных заявок на участие в данном событии
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    private Long id;
    private UserShortDto initiator;
    private Boolean isPaid; //Нужно ли оплачивать участие
    private String title; //Заголовок
    private Integer views; //Количество просмотрев события
}
