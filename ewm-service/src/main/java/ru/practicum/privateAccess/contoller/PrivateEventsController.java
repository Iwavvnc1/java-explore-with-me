package ru.practicum.privateAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.request.dto.ParticipationRequestDto;
import ru.practicum.privateAccess.service.PrivateEventsService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/users/{userId}/events")
public class PrivateEventsController {
    private final PrivateEventsService service;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@PathVariable Long userId,
                                                @RequestBody @Valid NewEventDto eventDto) {
        return new ResponseEntity<>(service.createEvent(userId, eventDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getEventsCurrentUser(@PathVariable Long userId,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(service.getEventsCurrentUser(userId, from, size), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        return new ResponseEntity<>(service.getEventById(userId, eventId), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<ParticipationRequestDto> getEventRequestsById(@PathVariable Long userId,
                                                                        @PathVariable Long eventId) {
        return new ResponseEntity<>(service.getEventRequestsById(userId, eventId), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEventById(@PathVariable Long userId,
                                                    @PathVariable Long eventId,
                                                    @RequestBody UpdateEventUserRequest eventDto) {
        return new ResponseEntity<>(service.updateEventById(userId, eventId, eventDto), HttpStatus.OK);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateEventRequestsStatusById(@PathVariable Long userId,
                                                                                        @PathVariable Long eventId,
                                                                                        @RequestBody
                                                                                        EventRequestStatusUpdateRequest
                                                                                                updateRequest) {
        return new ResponseEntity<>(service.updateEventRequestStatusById(userId, eventId, updateRequest), HttpStatus.OK);
    }
}
