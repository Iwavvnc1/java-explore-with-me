package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.privateAccess.service.PrivateRequestService;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
public class PrivateRequestController {
    private final PrivateRequestService service;

    @GetMapping
    public ResponseEntity<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        return new ResponseEntity<>(service.getRequests(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> addRequest(@PathVariable Long userId,
                                                              @RequestParam(required = false) Long eventId) {
        return new ResponseEntity<>(service.addRequest(userId, eventId), HttpStatus.OK);
    }
}
