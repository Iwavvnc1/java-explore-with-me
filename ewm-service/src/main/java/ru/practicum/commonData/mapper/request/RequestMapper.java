package ru.practicum.commonData.mapper.request;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.request.ParticipationRequest;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.commonData.model.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.commonData.enums.Status.CONFIRMED;
import static ru.practicum.commonData.enums.Status.PENDING;

@UtilityClass
public class RequestMapper {
    public ParticipationRequest toParticipationRequest(User user, Event event) {
        return ParticipationRequest.builder()
                .requester(user)
                .created(LocalDateTime.now())
                .status(event.getRequestModeration() ? PENDING : CONFIRMED)
                .event(event)
                .build();
    }

    public ParticipationRequestDto toParticipationRequestDto(ParticipationRequest requestDto) {
        return ParticipationRequestDto.builder()
                .requester(requestDto.getRequester().getId())
                .created(requestDto.getCreated())
                .status(requestDto.getStatus())
                .event(requestDto.getEvent().getId())
                .id(requestDto.getId())
                .build();
    }

    public List<ParticipationRequestDto> toParticipationRequestDtoList(
            List<ParticipationRequest> participationRequests) {
        return participationRequests.stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }
}
