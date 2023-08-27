package ru.practicum.commonData.model.compilation;

import lombok.*;
import ru.practicum.commonData.model.event.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events;
    private Boolean pinned; //Закреплена ли подборка на главной странице сайта
    private String title; //Заголовок подборки
}
