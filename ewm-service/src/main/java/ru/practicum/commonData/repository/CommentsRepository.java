package ru.practicum.commonData.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.commonData.model.comment.Comment;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAuthorIdAndDeleteStatusIsFalse(Long userId);

    List<Comment> findAllByEventIdAndDeleteStatusIsFalse(Long eventId);

    @Query("SELECT c.event.id, c FROM Comment c WHERE c.event.id IN :eventIds and c.deleteStatus = false ")
    List<Object[]> findCommentsByEventIds(@Param("eventIds") List<Long> eventIds);

    /*@Query(value = "SELECT c.event_id AS eventId, " +
            "(SELECT co FROM comments co WHERE co.delete_status = false AND co.event_id = c.event_id) AS comment " +
            "FROM comments c " +
            "WHERE c.delete_status = false AND c.event_id IN :eventIds " +
            "GROUP BY c.event_id", nativeQuery = true)
    List<CommentEvent> findAllForEvent(@Param("eventIds") List<Long> eventIds);*/

}
