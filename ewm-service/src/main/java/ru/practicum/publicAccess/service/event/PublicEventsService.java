package ru.practicum.publicAccess.service.event;

import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.PublicEventsParam;

import java.util.List;

public interface PublicEventsService {
    EventDto getEventById(Long id);

    List<EventDto> getEvents(PublicEventsParam param);
}
