package ru.practicum.commonData.model.compilation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.event.dto.EventShortDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Compilation")
public class Compilation {
    private List<EventShortDto> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isPinned; //Закреплена ли подборка на главной странице сайта

    private String title; //Заголовок подборки
}
