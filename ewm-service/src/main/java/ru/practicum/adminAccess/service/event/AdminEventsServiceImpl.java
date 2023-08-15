package ru.practicum.adminAccess.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.event.dto.AdminEventsParam;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.NewEventDto;

import java.util.List;

@Service
public class AdminEventsServiceImpl implements AdminEventsService {

    public List<EventDto> getEvents(AdminEventsParam requestParam) {
        return null;
    }

    public EventDto updateEvent(Long eventId, NewEventDto eventDto) {
        return null;
    }
}
