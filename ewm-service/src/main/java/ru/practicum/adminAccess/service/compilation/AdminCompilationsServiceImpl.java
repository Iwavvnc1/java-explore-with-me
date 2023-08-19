package ru.practicum.adminAccess.service.compilation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.model.compilation.Compilation;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;
import ru.practicum.commonData.repository.CompilationRepository;
import ru.practicum.commonData.repository.EventRepository;

import java.util.Set;

import static ru.practicum.commonData.mapper.compilation.CompilationMapper.*;

@RequiredArgsConstructor
@Service
public class AdminCompilationsServiceImpl implements AdminCompilationsService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = toCompilationFromNewCompilationDto(compilationDto);
        compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()).orElse(Set.of()));
        try {
            compilation = compilationRepository.save(compilation);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(), e);
        }
        return toCompilationDtoFromCompilation(compilation);
    }

    @Transactional
    public void deleteCompilation(Long compId) {
        try {
            compilationRepository.deleteById(compId);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException(String.format("Compilation with id=%d was not found", compId));
        }
    }

    @Transactional
    @SneakyThrows
    public CompilationDto updateCompilation(Long compId, NewCompilationDto compilationDto) {
        Compilation compilationOld = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", compId)));
        Compilation compilationUpdate = toCompilationFromNewCompilationDto(compilationDto);
        compilationUpdate.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()).orElse(Set.of()));
        objectMapper.updateValue(compilationOld, compilationUpdate);
        compilationRepository.save(compilationOld);
        return toCompilationDtoFromCompilation(compilationOld);
    }
}
