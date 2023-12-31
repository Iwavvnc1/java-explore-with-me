package ru.practicum.commonData.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
            "AND (e.eventDate BETWEEN COALESCE(?4, e.eventDate) AND COALESCE(?5, e.eventDate)) ")
    Page<Event> findAllByParams(List<Long> users, List<State> states, List<Long> categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);


    @Query(value = "SELECT * " +
            "FROM events " +
            "WHERE (events.category_id IN :categories or :categories is null) " +
            "AND (events.paid = :paid or :paid is null) " +
            "AND (events.event_date > COALESCE(:rangeStart, NOW())) " +
            "AND ((events.event_date < :rangeEnd) or DATE(:rangeEnd) is null) " +
            "AND (events.annotation ILIKE :text or :text is null " +
            "OR events.description ILIKE :text or :text is null) " +
            "AND (events.published_on is not null)", nativeQuery = true)
    Page<Event> findAllByPublicParamsAvailable(@Param("categories") List<Long> categories,
                                               @Param("paid") Boolean paid,
                                               @Param("rangeStart") LocalDateTime rangeStart,
                                               @Param("rangeEnd") LocalDateTime rangeEnd,
                                               @Param("text") String text,
                                               Pageable pageable);

    @Query(value = "SELECT * " +
            "FROM events " +
            "WHERE (events.category_id IN :categories or :categories is null) " +
            "AND (events.paid = :paid or :paid is null) " +
            "AND (events.event_date > COALESCE(:rangeStart, NOW())) " +
            "AND ((events.event_date < :rangeEnd) or DATE(:rangeEnd) is null) " +
            "AND (events.annotation ILIKE :text or :text is null " +
            "OR events.description ILIKE :text or :text is null) " +
            "AND (events.published_on is not null)", nativeQuery = true)
    Page<Event> findAllByPublicParams(@Param("categories") List<Long> categories,
                                      @Param("paid") Boolean paid,
                                      @Param("rangeStart") LocalDateTime rangeStart,
                                      @Param("rangeEnd") LocalDateTime rangeEnd,
                                      @Param("text") String text,
                                      Pageable pageable);

    boolean existsByCategoryId(Long id);

    Optional<Event> findByIdIsAndInitiatorId(Long eventId, Long userId);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.id = ?1 " +
            "AND (e.publishedOn IS NOT NULL ) ")
    Optional<Event> findByIdAndPublishedOn(Long id);

    Optional<Set<Event>> findAllByIdIn(Set<Long> ids);
}
