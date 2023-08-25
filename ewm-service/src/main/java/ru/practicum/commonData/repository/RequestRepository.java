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

    @Query("select new ru.practicum.commonData.model.request.dto.ConfirmedRequest(r.event.id,COUNT(distinct r.id)) " +
            "FROM ParticipationRequest r " +
            "where r.status = 'CONFIRMED' and r.id IN :eventsIds group by r.event.id")
    List<ConfirmedRequest> findConfirmedRequest(@Param("eventsIds") List<Long> eventsIds);

    Integer countAllByEventIdAndStatus(Long id, Status status);
}
