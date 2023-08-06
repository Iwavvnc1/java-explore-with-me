package controller;

import dto.EndpointHitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    public ResponseEntity<Object> saveStatistic(@RequestBody EndpointHitDto endpointHit) {
        log.info("save Statistic");
        return ResponseEntity.ok().body(statService.saveStatistic(endpointHit));
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistic(@RequestParam LocalDateTime start,
                                               @RequestParam LocalDateTime end,
                                               @RequestParam List<String> uris,
                                               @RequestParam Boolean unique) {
        log.info("get Statistic");
        return ResponseEntity.ok().body(statService.getStatistic(start, end, uris, unique));
    }
}
