package ru.practicum.adminAccess.service.event;

import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.NewEventDto;

import java.util.List;

public interface AdminEventsService {
    List<EventDto> getEvents(AdminEventsParam requestParam);

    EventDto updateEvent(Long eventId, NewEventDto eventDto);
}
