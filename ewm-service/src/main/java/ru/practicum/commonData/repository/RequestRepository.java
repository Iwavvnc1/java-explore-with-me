package ru.practicum.commonData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.model.request.ParticipationRequest;
import ru.practicum.commonData.model.request.dto.ConfirmedRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Optional<ParticipationRequest> findByIdAndRequesterId(Long requestId, Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<ParticipationRequest> findAllByIdIn(Set<Long> requestIds);

    @Query("select new ru.practicum.commonData.model.request.dto.ConfirmedRequest(r.event.id,COUNT(distinct r)) " +
            "FROM ParticipationRequest r " +
            "where r.status = 'CONFIRMED' and r.event.id IN :eventIds group by r.event.id")
    List<ConfirmedRequest> findConfirmedRequests(@Param("eventIds") List<Long> eventIds);

    @Query("select new ru.practicum.commonData.model.request.dto.ConfirmedRequest(r.event.id,COUNT(distinct r)) " +
            "FROM ParticipationRequest r " +
            "where r.status = 'CONFIRMED' and r.event.id = :eventId group by r.event.id")
    Optional<ConfirmedRequest> findConfirmedRequest(@Param("eventId") Long eventId);

    Integer countAllByEventIdAndStatus(Long id, Status status);
}
