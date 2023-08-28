package ru.practicum.adminAccess.service.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.model.comment.Comment;
import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.repository.CommentsRepository;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.UserRepository;

import java.util.List;

import static ru.practicum.commonData.mapper.comment.CommentMapper.*;

@RequiredArgsConstructor
@Service
public class AdminCommentsServiceImpl implements AdminCommentsService {
    private final CommentsRepository commentsRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public void deleteComment(Long commId) {
        Comment comment = commentsRepository.findById(commId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment not found with id = %d", commId)));
        comment.setDeleteStatus(true);
        try {
            commentsRepository.save(comment);
            commentsRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage());
        }
    }

    @Override
    public List<CommentDto> getAllByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User not found with id = %d", userId));
        }
        return toCommentDtoList(commentsRepository.findAllByAuthorIdAndDeleteStatusIsFalse(userId));
    }

    @Override
    public List<CommentDto> getAllByEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(String.format("Event not found with id = %d", eventId));
        }
        return toCommentDtoList(commentsRepository.findAllByEventIdAndDeleteStatusIsFalse(eventId));
    }
}
