package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.ViewStats;
import ru.practicum.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public ResponseEntity<EndpointHitDto> saveStatistic(@RequestBody EndpointHitDto endpointHit) {
        log.info("save Statistic");
        return new ResponseEntity<>(statService.saveStatistic(endpointHit), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStats>> getStatistic(@RequestParam
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                        @RequestParam
                                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                        @RequestParam(required = false) List<String> uris,
                                                        @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("get Statistic");
        return ResponseEntity.ok().body(statService.getStatistic(start, end, uris, unique));
    }
}
