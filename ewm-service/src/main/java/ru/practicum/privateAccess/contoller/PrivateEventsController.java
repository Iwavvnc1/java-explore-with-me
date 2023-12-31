package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.privateAccess.service.event.PrivateEventsService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class PrivateEventsController {
    private final PrivateEventsService service;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@PathVariable Long userId,
                                                @RequestBody @Valid NewEventDto eventDto) {
        log.info("Request PEC POST /users/{}/events with dto = {}", userId, eventDto);
        return new ResponseEntity<>(service.createEvent(userId, eventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventShortDto>> getEventsCurrentUser(@PathVariable Long userId,
                                                                    @RequestParam(defaultValue = "0")
                                                                    @PositiveOrZero Integer from,
                                                                    @RequestParam(defaultValue = "10")
                                                                        @Positive Integer size) {
        log.info("Request PEC GET /users/{}/events with from = {}, size = {}", userId, from, size);
        return ResponseEntity.ok(service.getEventsCurrentUser(userId, from, size));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        log.info("Request PEC GET /users/{}/events/{}", userId, eventId);
        return ResponseEntity.ok(service.getEventById(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getEventRequestsByEventId(@PathVariable Long userId,
                                                                                   @PathVariable Long eventId) {
        log.info("Request PEC GET /users/{}/events/{}", userId, eventId);
        return ResponseEntity.ok(service.getEventRequestsById(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEventById(@PathVariable Long userId,
                                                    @PathVariable Long eventId,
                                                    @RequestBody @Valid UpdateEventUser eventDto) {
        log.info("Request PEC PATCH /users/{}/events/{} with dto = {}", userId, eventId, eventDto);
        return ResponseEntity.ok(service.updateEventById(userId, eventId, eventDto));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequestsStatusById(@PathVariable Long userId,
                                                                                        @PathVariable Long eventId,
                                                                                        @RequestBody
                                                                                        EventRequestStatusUpdateRequest
                                                                                                updateRequest) {
        log.info("Request PEC PATCH /users/{}/events/{} with dto = {}", userId, eventId, updateRequest);
        return ResponseEntity.ok(service.updateEventRequestStatusById(userId, eventId, updateRequest));
    }
}
