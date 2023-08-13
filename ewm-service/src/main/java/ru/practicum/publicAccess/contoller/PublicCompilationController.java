package ru.practicum.publicAccess.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.publicAccess.service.PublicCompilationService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping("/compilations")
    public ResponseEntity<CompilationDto> getCompilationsEvents(@RequestParam(required = false) Boolean pinned,
                                                                @RequestParam(defaultValue = "0") Integer From,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(compilationService.getCompilations());
    }
}
