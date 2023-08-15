package ru.practicum.publicAccess.service.compilation;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;

import java.util.List;

@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {

    public List<CompilationDto> getCompilationsEvents(Boolean pinned, Integer from, Integer size) {
        return null;
    }

    public CompilationDto getCompilationsEventById(Long compId) {
        return null;
    }
}
