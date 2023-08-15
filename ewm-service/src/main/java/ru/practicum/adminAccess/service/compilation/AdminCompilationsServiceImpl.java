package ru.practicum.adminAccess.service.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;

@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        return null;
    }

    public void deleteCompilation(Long compId) {

    }

    public CompilationDto updateCompilation(Long compId, NewCompilationDto compilationDto) {
        return null;
    }
}
