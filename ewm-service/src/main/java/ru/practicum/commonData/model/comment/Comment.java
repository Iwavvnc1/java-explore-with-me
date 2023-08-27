package ru.practicum.commonData.model.comment;

import lombok.*;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    private String text;
    private Boolean deleteStatus;
    private LocalDateTime createdOn;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
    private Long likes;
}
