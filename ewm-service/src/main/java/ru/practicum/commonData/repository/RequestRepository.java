package ru.practicum.commonData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.commonData.model.request.ParticipationRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Optional<ParticipationRequest> findByIdAndRequesterId(Long requestId, Long userId);

    List<ParticipationRequest> findAllByEventId(Long eventId);

    List<ParticipationRequest> findAllByRequesterId(Long userId);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    List<ParticipationRequest> findAllByIdIn(List<Long> requestIds);
}
