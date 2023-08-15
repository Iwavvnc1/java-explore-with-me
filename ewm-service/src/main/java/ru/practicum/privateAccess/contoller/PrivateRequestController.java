package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.privateAccess.service.request.PrivateRequestServiceImpl;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestServiceImpl service;

    @GetMapping
    public ResponseEntity<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        log.info("Request GET /users/{}/requests", userId);
        return new ResponseEntity<>(service.getRequests(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable Long userId,
                                                              @RequestParam(required = false) Long eventId) {
        log.info("Request GET /users/{}/requests with eventID = {}", userId, eventId);
        return new ResponseEntity<>(service.addRequest(userId, eventId), HttpStatus.OK);
    }
}
