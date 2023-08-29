package ru.practicum.privateAccess.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.comment.Comment;
import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.model.comment.dto.NewCommentDto;
import ru.practicum.commonData.repository.CommentsRepository;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.UserRepository;


import java.util.List;

import static ru.practicum.commonData.mapper.comment.CommentMapper.*;


@RequiredArgsConstructor
@Service
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto createComment(Long userId, Long eventId, NewCommentDto commentDto) {

        Comment comment = toCommentFromNewCommentDto(commentDto, userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException(String.format("User not found with id = %d", userId))),
                eventRepository.findById(eventId)
                        .orElseThrow(() ->
                                new NotFoundException(String.format("Event not found with id = %d", eventId))));
        try {
            comment = commentsRepository.save(comment);
            commentsRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        return toCommentDtoFromComment(comment);
    }

    @Transactional
    @Override
    public CommentDto updateComment(Long userId, Long commId, NewCommentDto commentDto) {
        checkUser(userId);
        Comment comment = commentsRepository.findById(commId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment not found with id = %d", commId)));
        comment.setText(commentDto.getText());
        try {
            comment = commentsRepository.save(comment);
            commentsRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
        return toCommentDtoFromComment(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> getAllByUser(Long userId) {
        checkUser(userId);
        List<Comment> comments = commentsRepository.findAllByAuthorIdAndDeleteStatusIsFalse(userId);
        return toCommentDtoList(comments);
    }

    @Transactional
    @Override
    public void deleteComment(Long userId, Long commId) {
        Comment comment = checkAndGetComment(userId,commId);
        comment.setDeleteStatus(true);
        try {
            commentsRepository.save(comment);
            commentsRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void addLike(Long userId, Long commId) {
        checkUser(userId);
        Comment comment = commentsRepository.findById(commId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment not found with id = %d", commId)));
        comment.setLikes(comment.getLikes() + 1L);
        try {
            commentsRepository.save(comment);
            commentsRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
    }

    private Comment checkAndGetComment(Long userId, Long commId) {
        checkUser(userId);
        Comment comment = commentsRepository.findById(commId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment not found with id = %d", commId)));
        if (comment.getAuthor().getId().equals(userId)) {
            return comment;
        }
            throw new NotFoundException(String
                    .format("Comment not found with id = %d, with userId = %d", commId, userId));
    }

    private void checkUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User not found with id = %d", userId));
        }
    }
}
