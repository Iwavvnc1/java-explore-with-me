package ru.practicum.commonData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.commonData.model.comment.Comment;
import ru.practicum.commonData.model.comment.dto.CommentEvent;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorIdAndDeleteStatusIsFalse(Long userId);

    List<Comment> findAllByEventIdAndDeleteStatusIsFalse(Long eventId);

   @Query("select new ru.practicum.commonData.model.comment.dto.CommentEvent(c.event.id, " +
           "(select co from Comment co where co.deleteStatus = false and co.event.id = c.event.id)) " +
           "from Comment c " +
           "where c.deleteStatus = false and c.event.id in :eventIds ")
    List<CommentEvent> findAllForEvent(@Param("eventIds") List<Long> eventIds);
}
