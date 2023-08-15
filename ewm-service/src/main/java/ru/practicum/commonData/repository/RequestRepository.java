package ru.practicum.commonData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.commonData.model.request.ParticipationRequest;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {
}
