package ru.practicum.commonData.model.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private Status status;
}
