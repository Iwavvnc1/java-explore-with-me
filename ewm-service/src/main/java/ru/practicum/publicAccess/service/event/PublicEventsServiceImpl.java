package ru.practicum.publicAccess.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.mapper.event.EventMapper;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.PublicEventsParam;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.customPageRequest.CustomPageRequest;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    private final EventRepository eventRepository;

    @Override
    public EventDto getEventById(Long id) {
        return EventMapper.toEventDtoFromEventViews(eventRepository.findByIdAndPublishedOn(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", id))));
    }

    @Override
    public List<EventDto> getEvents(PublicEventsParam param) {
        PageRequest pageRequest;
        if (param.getRangeStart() != null && param.getRangeStart().isAfter(param.getRangeEnd())) {
            throw new NotValidException("");
        }
        if (param.getSort() == null || param.getSort().isEmpty()) {
            pageRequest = CustomPageRequest.of(param.getFrom(), param.getSize());
        } else {
            if (param.getSort().equals("EVENT_DATE")) {
                pageRequest = CustomPageRequest.of(param.getFrom(), param.getSize(), Sort.by("eventDate"));
            } else {
                pageRequest = CustomPageRequest.of(param.getFrom(), param.getSize(), Sort.by("views"));
            }
        }
        if (param.getOnlyAvailable() != null && param.getOnlyAvailable()) {
            return eventRepository.findAllByPublicParamsAvailable(param.getCategories(), param.getPaid(),
                            param.getRangeStart(), param.getRangeEnd(), param.getText(), pageRequest).stream()
                    .map(EventMapper::toEventDtoFromEvent).collect(Collectors.toList());
        }
        return eventRepository.findAllByPublicParams(param.getCategories(), param.getPaid(),
                        param.getRangeStart(), param.getRangeEnd(), param.getText(), pageRequest).stream()
                .map(EventMapper::toEventDtoFromEvent).collect(Collectors.toList());
    }
}
