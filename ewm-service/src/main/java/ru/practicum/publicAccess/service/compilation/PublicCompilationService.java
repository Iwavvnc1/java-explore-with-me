package ru.practicum.publicAccess.service.compilation;

import ru.practicum.commonData.model.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getCompilationsEvents(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationsEventById(Long compId);
}
