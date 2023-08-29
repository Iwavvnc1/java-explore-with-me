package ru.practicum.commonData.mapper.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.mapper.user.UserMapper;
import ru.practicum.commonData.model.comment.Comment;
import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.model.comment.dto.NewCommentDto;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDtoFromComment(Comment comment) {
        return CommentDto.builder()
                .text(comment.getText())
                .createdOn(comment.getCreatedOn())
                .author(UserMapper.toUserDtoFromUser(comment.getAuthor()))
                .likes(comment.getLikes())
                .build();
    }

    public Comment toCommentFromNewCommentDto(NewCommentDto commentDto, User user, Event event) {
        return Comment.builder()
                .text(commentDto.getText())
                .likes(0L)
                .deleteStatus(false)
                .author(user)
                .event(event)
                .build();
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::toCommentDtoFromComment).collect(Collectors.toList());
    }
}
