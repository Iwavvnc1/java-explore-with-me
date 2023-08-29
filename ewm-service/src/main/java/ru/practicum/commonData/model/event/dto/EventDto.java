package ru.practicum.commonData.model.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.category.dto.CategoryDto;
import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.model.location.dto.LocationDto;
import ru.practicum.commonData.model.user.dto.UserDto;
import ru.practicum.commonData.utils.FormatDate;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventDto {
    private Long id;
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime eventDate;
    private UserDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private State state;
    private String title;
    private Long views;
    private List<CommentDto> comment;
}
