package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.privateAccess.service.request.PrivateRequestServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestServiceImpl service;

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getRequests(@PathVariable Long userId) {
        log.info("Request PRC GET /users/{}/requests", userId);
        return new ResponseEntity<>(service.getRequests(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable Long userId,
                                                              @RequestParam Long eventId) {
        log.info("Request PRC POST /users/{}/requests with eventID = {}", userId, eventId);
        return new ResponseEntity<>(service.addRequest(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelRequest(@PathVariable Long userId,
                                                                 @PathVariable Long requestId) {
        log.info("Request PRC PATCH /users/{}/requests with requestID = {}", userId, requestId);
        return new ResponseEntity<>(service.cancelRequest(userId, requestId), HttpStatus.OK);
    }
}
