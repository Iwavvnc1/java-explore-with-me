package ru.practicum.privateAccess.service.comment;

import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.model.comment.dto.NewCommentDto;

import java.util.List;

public interface PrivateCommentService {
    CommentDto createComment(Long userId, Long eventId, NewCommentDto commentDto);

    CommentDto updateComment(Long userId, Long commId, NewCommentDto commentDto);

    List<CommentDto> getAllByUser(Long userId);

    void deleteComment(Long userId, Long commId);

    void addLike(Long userId, Long commId);
}
