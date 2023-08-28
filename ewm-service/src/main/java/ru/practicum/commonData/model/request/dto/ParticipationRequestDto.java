package ru.practicum.commonData.model.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.utils.FormatDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private Status status;
}
