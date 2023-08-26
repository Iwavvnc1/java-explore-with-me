package ru.practicum.adminAccess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminAccess.service.event.AdminEventsService;
import ru.practicum.commonData.enums.State;
import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.UpdateEventAdmin;
import ru.practicum.commonData.utils.FormatDate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/events")
@Validated
public class AdminEventsController {
    private final AdminEventsService service;

    @GetMapping
    public ResponseEntity<List<EventDto>> getEvents(@RequestParam(required = false) List<Long> users,
                                                    @RequestParam(required = false) List<State> states,
                                                    @RequestParam(required = false) List<Long> categories,
                                                    @RequestParam(required = false)
                                                    @DateTimeFormat(pattern = FormatDate.TIME_FORMAT)
                                                    LocalDateTime rangeStart,
                                                    @RequestParam(required = false)
                                                    @DateTimeFormat(pattern = FormatDate.TIME_FORMAT)
                                                    LocalDateTime rangeEnd,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        AdminEventsParam requestParam = AdminEventsParam.builder()
                .users(users)
                .states(states)
                .categories(categories)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .from(from)
                .size(size)
                .build();
        log.info("Request AEC GET /admin/events with param = {}", requestParam);
        return ResponseEntity.ok(service.getEvents(requestParam));
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId,
                                                @RequestBody @Valid UpdateEventAdmin eventDto) {
        log.info("Request AEC PATCH /admin/events/{} with dto = {}", eventId, eventDto);
        return ResponseEntity.ok(service.updateEvent(eventId, eventDto));
    }
}
