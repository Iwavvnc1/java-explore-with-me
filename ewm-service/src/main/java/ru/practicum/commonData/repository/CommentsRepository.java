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

    @Query(value = "SELECT c.event_id AS eventId, " +
            "(SELECT co FROM comments co WHERE co.delete_status = false AND co.event_id = c.event_id) AS comment " +
            "FROM comments c " +
            "WHERE c.delete_status = false AND c.event_id IN :eventIds " +
            "GROUP BY c.event_id", nativeQuery = true)
    List<CommentEvent> findAllForEvent(@Param("eventIds") List<Long> eventIds);

}
