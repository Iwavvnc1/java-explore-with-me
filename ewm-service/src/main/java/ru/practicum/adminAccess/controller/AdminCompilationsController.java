package ru.practicum.adminAccess.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminAccess.service.compilation.AdminCompilationsServiceImpl;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/compilations")
public class AdminCompilationsController {
    private final AdminCompilationsServiceImpl service;

    @PostMapping
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Request POST /admin/compilations with dto = {}", compilationDto);
        return new ResponseEntity<>(service.createCompilation(compilationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity deleteCompilation(@PathVariable Long compId) {
        service.deleteCompilation(compId);
        log.info("Request DELETE /admin/compilations/{}", compId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable Long compId,
                                                            @RequestBody @Valid NewCompilationDto compilationDto) {
        log.info("Request PATCH /admin/compilations/{} with dto = {}", compId, compilationDto);
        return new ResponseEntity<>(service.updateCompilation(compId, compilationDto), HttpStatus.OK);
    }
}
