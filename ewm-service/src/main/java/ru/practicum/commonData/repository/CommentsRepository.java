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
}
