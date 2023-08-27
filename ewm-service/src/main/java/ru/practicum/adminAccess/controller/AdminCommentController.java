package ru.practicum.adminAccess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminAccess.service.comment.AdminCommentsService;
import ru.practicum.commonData.model.comment.dto.CommentDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/comments")
public class AdminCommentController {
    private final AdminCommentsService service;

    @DeleteMapping("/{commId}")
    public ResponseEntity deleteComment(@PathVariable Long commId) {
        service.deleteCategory(commId);
        log.info("Request ACommC DELETE /admin/comments/{}", commId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@PathVariable Long userId) {
        log.info("Request ACommC GET /admin/comments/users/{}", userId);
        return ResponseEntity.ok(service.getAllByUser(userId));
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<List<CommentDto>> getAllCommentByEvent(@PathVariable Long eventId) {
        log.info("Request ACommC GET /admin/comments/events/{}", eventId);
        return ResponseEntity.ok(service.getAllByEvent(eventId));
    }
}
