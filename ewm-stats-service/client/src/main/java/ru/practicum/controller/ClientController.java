package ru.practicum.controller;

import ru.practicum.client.StatClient;
import ru.practicum.dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ClientController {
    private final StatClient statClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveStatistic(@RequestBody EndpointHitDto endpointHit) {
        log.info("Client:save Statistic");
        return ResponseEntity.ok().body(statClient.saveStats(endpointHit));
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistic(@RequestParam LocalDateTime start,
                                               @RequestParam LocalDateTime end,
                                               @RequestParam List<String> uris,
                                               @RequestParam Boolean unique) {
        log.info("Client:get Statistic");
        return ResponseEntity.ok().body(statClient.getStats(start, end, uris, unique));
    }
}
