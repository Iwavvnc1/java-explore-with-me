package ru.practicum.publicAccess.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.commonData.statsServiceApi.StatsServiceApi;
import ru.practicum.commonData.customPageRequest.CustomPageRequest;
import ru.practicum.commonData.enums.Status;
import ru.practicum.commonData.exceptions.NotFoundException;
import ru.practicum.commonData.exceptions.NotValidException;
import ru.practicum.commonData.mapper.event.EventMapper;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.PublicEventsParam;
import ru.practicum.commonData.repository.EventRepository;
import ru.practicum.commonData.repository.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final StatsServiceApi statsService;

    @Override
    public EventDto getEventById(Long id) {
        EventDto eventDto = EventMapper.toEventDtoFromEventViews(eventRepository.findByIdAndPublishedOn(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id=%d was not found", id))));
        eventDto.setConfirmedRequests(requestRepository.countAllByEventIdAndStatus(id, Status.CONFIRMED));
        eventDto.setViews(statsService.getViews(id));
        return eventDto;
    }

    @Override
    public List<EventDto> getEvents(PublicEventsParam param) {
        if (param.getRangeStart() != null && param.getRangeStart().isAfter(param.getRangeEnd())) {
            throw new NotValidException("");
        }
        PageRequest pageRequest = CustomPageRequest.of(param.getFrom(), param.getSize(), Sort.by(param.getSort()));
        Page<Event> result;
        if (param.getOnlyAvailable()) {
            try {
                result = eventRepository.findAllByPublicParamsAvailable(param.getCategories(), param.getPaid(),
                        param.getRangeStart(), param.getRangeEnd(), param.getText(),
                        pageRequest);
            } catch (DataIntegrityViolationException e) {
                throw new NotValidException(e.getMessage(), e);
            }
            return result.stream().map(EventMapper::toEventDtoFromEvent)
                    .collect(Collectors.toList());
        }
        try {
            result = eventRepository.findAllByPublicParams(param.getCategories(), param.getPaid(),
                    param.getRangeStart(), param.getRangeEnd(), param.getText(),
                    pageRequest);
        } catch (DataIntegrityViolationException e) {
            throw new NotValidException(e.getMessage(), e);
        }
        return result.stream().map(EventMapper::toEventDtoFromEvent).collect(Collectors.toList());
    }
}
