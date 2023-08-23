package ru.practicum.adminAccess.service.compilation;

import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;
import ru.practicum.commonData.model.compilation.dto.UpdateCompilationDto;

public interface AdminCompilationsService {
    CompilationDto createCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationDto compilationDto);
}
