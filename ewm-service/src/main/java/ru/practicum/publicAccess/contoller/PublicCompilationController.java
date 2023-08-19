package ru.practicum.publicAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.publicAccess.service.compilation.PublicCompilationServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/compilations")
public class PublicCompilationController {
    private final PublicCompilationServiceImpl service;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getCompilationsEvents(@RequestParam(required = false) Boolean pinned,
                                                                      @RequestParam(defaultValue = "0") Integer from,
                                                                      @RequestParam(defaultValue = "10") Integer size) {
        log.info("Request PubCompC GET /compilations with pinned = {}, from = {}, size = {}", pinned, from, size);
        return new ResponseEntity<>(service.getCompilationsEvents(pinned, from, size), HttpStatus.OK);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getCompilationsEventById(@PathVariable Long compId) {
        log.info("Request PubCompC GET /compilations/{}", compId);
        return new ResponseEntity<>(service.getCompilationsEventById(compId), HttpStatus.OK);
    }
}
