package ru.practicum.commonData.model.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.model.user.dto.UserDto;
import ru.practicum.commonData.utils.FormatDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FormatDate.TIME_FORMAT)
    private LocalDateTime createdOn;
    private UserDto author;
    private Long likes;
}
