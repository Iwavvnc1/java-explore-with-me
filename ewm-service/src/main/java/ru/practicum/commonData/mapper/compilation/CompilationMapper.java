package ru.practicum.commonData.mapper.compilation;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.mapper.event.EventMapper;
import ru.practicum.commonData.model.compilation.Compilation;
import ru.practicum.commonData.model.compilation.dto.CompilationDto;
import ru.practicum.commonData.model.compilation.dto.NewCompilationDto;

import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {
    public CompilationDto toCompilationDtoFromCompilation(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(compilation.getEvents().stream().map(EventMapper::toEventShortDtoFromEvent)
                        .collect(Collectors.toSet()))
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .build();
    }

    public Compilation toCompilationFromNewCompilationDto(NewCompilationDto compilationDto) {
        return Compilation.builder()
                .title(compilationDto.getTitle())
                .pinned(compilationDto.getPinned())
                .build();
    }
}
