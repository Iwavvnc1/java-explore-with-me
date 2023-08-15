package ru.practicum.adminAccess.service.compilation;

import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;

public interface AdminCompilationsService {
    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, NewCompilationDto compilationDto);
}
