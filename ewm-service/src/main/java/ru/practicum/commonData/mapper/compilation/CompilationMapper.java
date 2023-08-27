package ru.practicum.commonData.mapper.compilation;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.mapper.event.EventMapper;
import ru.practicum.commonData.model.compilation.Compilation;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;
import ru.practicum.commonData.model.compilation.dto.UpdateCompilationDto;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public CompilationDto toCompilationDtoFromCompilation(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(compilation.getEvents() == null || compilation.getEvents().isEmpty() ?
                        Set.of() : compilation.getEvents().stream().map(EventMapper::toEventShortDtoFromEvent)
                        .collect(Collectors.toSet()))
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
    }

    public Compilation toCompilationFromNewCompilationDto(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned() == null ? false : compilationDto.getPinned())
                .build();
    }

    public Compilation updateCompilationFromUpdateCompilationDto(UpdateCompilationDto compilationDto,
                                                          Compilation compilation) {
        return Compilation.builder()
                .id(compilation.getId())
                .events(compilation.getEvents())
                .title(compilationDto.getTitle()  != null  && !compilationDto.getTitle().isBlank()
                        ? compilationDto.getTitle() : compilation.getTitle())
                .pinned(compilationDto.getPinned() != null ? compilationDto.getPinned() : compilation.getPinned())
                .build();
    }
}
