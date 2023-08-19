package ru.practicum.commonData.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.event.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "compilations")
public class Compilation {
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean pinned; //Закреплена ли подборка на главной странице сайта

    private String title; //Заголовок подборки
}
