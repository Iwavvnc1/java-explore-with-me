package ru.practicum.publicAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.PublicEventsParam;
import ru.practicum.publicAccess.service.event.PublicEventsServiceImpl;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class PublicEventsController {
    private final PublicEventsServiceImpl service;
    @GetMapping
    public ResponseEntity<List<EventDto>> getEvents(@RequestParam(required = false) String text,
                                                       @RequestParam(required = false) List<Long> categories,
                                                       @RequestParam(required = false) Boolean paid,
                                                       @RequestParam(required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                           LocalDateTime rangeStart,
                                                       @RequestParam(required = false)
                                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                           LocalDateTime rangeEnd,
                                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                       @RequestParam(defaultValue = "EVENT_DATE") String sort,
                                                       @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                                       @RequestParam(defaultValue = "10") @Positive int size) {
        PublicEventsParam param = PublicEventsParam.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .sort(sort)
                .from(from)
                .size(size)
                .build();
        log.info("Request GET /events with param = {}", param);
        return new ResponseEntity<>(service.getEvents(param), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        log.info("Request GET /events/{}", id);
        return new ResponseEntity<>(service.getEventById(id), HttpStatus.OK);
    }
}
