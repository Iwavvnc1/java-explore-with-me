package ru.practicum.adminAccess.service.compilation;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.commonData.exceptions.ConflictException;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.model.compilation.Compilation;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;
import ru.practicum.commonData.model.compilation.dto.UpdateCompilationDto;
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
        if (compilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()).orElse(Set.of()));
        } else {
            compilation.setEvents(Set.of());
        }
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
    public CompilationDto updateCompilation(Long compId, UpdateCompilationDto compilationDto) {
        Compilation compilationOld = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id=%d was not found", compId)));
        if (compilationDto.getEvents() != null) {
            compilationOld.setEvents(eventRepository.findAllByIdIn(compilationDto.getEvents()).orElse(Set.of()));
        }
        Compilation updateCompilation = updateCompilationFromUpdateCompilationDto(compilationDto,compilationOld);
        if (updateCompilation.getEvents() == null) {
            updateCompilation.setEvents(Set.of());
        }
        try {
            updateCompilation = compilationRepository.save(updateCompilation);
            compilationRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException(e.getMessage(),e);
        }
        return toCompilationDtoFromCompilation(updateCompilation);
    }
}
