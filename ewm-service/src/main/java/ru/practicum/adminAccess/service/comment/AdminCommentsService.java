package ru.practicum.adminAccess.service.comment;

import ru.practicum.commonData.model.comment.dto.CommentDto;

import java.util.List;

public interface AdminCommentsService {
    void deleteCategory(Long commId);

    List<CommentDto> getAllByUser(Long userId);

    List<CommentDto> getAllByEvent(Long eventId);
}
