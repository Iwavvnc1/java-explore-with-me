package ru.practicum.commonData.model.request;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;
    @Enumerated(EnumType.STRING)
    private Status status;
}
