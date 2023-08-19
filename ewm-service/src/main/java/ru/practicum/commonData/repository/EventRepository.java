package ru.practicum.commonData.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.event.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByInitiatorId(Long id, Pageable pageable);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE (e.initiator.id IN ?1 or ?1 is null) " +
            "AND (e.state IN ?2 or ?2 is null) " +
            "AND (e.category.id IN ?3  or ?3 is null) " +
            "AND e.eventDate BETWEEN ?4 AND ?5 ")
    Page<Event> findAllByParams(List<Long> users, List<State> states, List<Long> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE (e.category.id IN ?1 or ?1 is null) " +
            "AND (e.paid IN ?2  or ?2 is null) " +
            "AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND (e.annotation  LIKE ?5 or ?5 is null) " +
            "AND (e.description  LIKE ?5  or ?5 is null) " +
            "AND e.confirmedRequests <> e.participantLimit ")
    Page<Event> findAllByPublicParamsAvailable(List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd, String text, Pageable pageable);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE (e.category.id IN ?1 or ?1 is null) " +
            "AND (e.paid IN ?2  or ?2 is null) " +
            "AND e.eventDate BETWEEN ?3 AND ?4 " +
            "AND (e.annotation LIKE ?5  or ?5 is null) " +
            "AND (e.description LIKE ?5  or ?5 is null) ")
    Page<Event> findAllByPublicParams(List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd, String text, Pageable pageable);

    boolean existsByCategoryId(Long id);

    Optional<Event> findByIdIsAndInitiatorId(Long eventId, Long userId);

    Optional<Set<Event>> findAllByIdIn(Set<Long> ids);
}
