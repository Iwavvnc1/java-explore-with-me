package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.comment.dto.CommentDto;
import ru.practicum.commonData.model.comment.dto.NewCommentDto;
import ru.practicum.privateAccess.service.comment.PrivateCommentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/comments")
public class PrivateCommentController {
    private final PrivateCommentService service;

    @PostMapping("/events/{eventId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long userId, @PathVariable Long eventId,
                                                    @RequestBody @Valid NewCommentDto commentDto) {
        log.info("Request PCommC POST /users/{}/comments/events/{}", userId, eventId);
        return ResponseEntity.ok(service.createComment(userId, eventId, commentDto));
    }

    @PutMapping ("/{commId}/like")
    public ResponseEntity addLikeComment(@PathVariable Long userId, @PathVariable Long commId) {
        service.addLike(userId,commId);
        log.info("Request PCommC PUT /users/{}/comments{}/like", userId, commId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commId}")
    public ResponseEntity deleteComment(@PathVariable Long userId, @PathVariable Long commId) {
        service.deleteComment(userId, commId);
        log.info("Request PCommC DELETE /users/{}/comments{}", userId, commId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{commId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long userId, @PathVariable Long commId,
                                                    @RequestBody @Valid NewCommentDto commentDto) {
        log.info("Request PCommC PATCH /users/{}/comments{}", userId, commId);
        return ResponseEntity.ok(service.updateComment(userId, commId, commentDto));
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentByUser(@PathVariable Long userId) {
        log.info("Request PCommC GET /users/{}/comments", userId);
        return ResponseEntity.ok(service.getAllByUser(userId));
    }
}