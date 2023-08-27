package ru.practicum.commonData.model.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.commonData.model.comment.Comment;

import java.util.List;

@Getter
@Setter
@Builder
public class CommentEvent {
    Long eventId;
    List<Comment> comments;

    public CommentEvent(Long eventId, List<Comment> comments) {
        this.eventId = eventId;
        this.comments = comments;
    }
}
