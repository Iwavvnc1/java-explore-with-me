package ru.practicum.commonData.model.event;

import lombok.*;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.category.Category;
import ru.practicum.commonData.model.location.Location;
import ru.practicum.commonData.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String annotation; //Краткое описание
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    private LocalDateTime createdOn; //Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String description; //Полное описание события
    private LocalDateTime eventDate; //Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @ManyToOne(fetch = FetchType.LAZY)
    private User initiator;
    @Embedded
    private Location location; //Широта и долгота места проведения события
    private Boolean paid; //Нужно ли оплачивать участие
    private Integer participantLimit; //default: 0. Ограничение на количество участников.
    // Значение 0 - означает отсутствие ограничения
    private LocalDateTime publishedOn; //Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private Boolean requestModeration; //default: true. Нужна ли пре-модерация заявок на участие
    @Enumerated(EnumType.STRING)
    private State state; //Список состояний жизненного цикла события
    private String title; //Заголовок
}
