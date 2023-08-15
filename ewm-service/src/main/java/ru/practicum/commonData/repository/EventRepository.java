package ru.practicum.commonData.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.commonData.model.event.Event;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event,Long> {
    Page<Event> findAllByInitiatorId(Long id, Pageable pageable);
    Optional<Event> findByIdIsAndInitiatorId(Long eventId, Long userId);
}
