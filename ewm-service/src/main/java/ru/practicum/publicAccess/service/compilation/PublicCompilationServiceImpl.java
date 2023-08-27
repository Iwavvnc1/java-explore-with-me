package ru.practicum.publicAccess.service.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.mapper.compilation.CompilationMapper;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.repository.CompilationRepository;
import ru.practicum.commonData.utils.customPageRequest.CustomPageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> getCompilationsEvents(Boolean pinned, Integer from, Integer size) {
        return compilationRepository.findAllByPinned(pinned, CustomPageRequest.of(from, size)).stream()
                .map(CompilationMapper::toCompilationDtoFromCompilation).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilationsEventById(Long compId) {
        return CompilationMapper.toCompilationDtoFromCompilation(compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String
                        .format("Compilation with id=%d was not found", compId))));
    }
}
