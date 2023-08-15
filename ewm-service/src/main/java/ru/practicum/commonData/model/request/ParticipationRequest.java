package ru.practicum.commonData.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "requests")
public class ParticipationRequest {
    private LocalDateTime created;
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User requester;
    private Status status;
}
