package ru.practicum.publicAccess.service.event;

import org.springframework.stereotype.Service;
import ru.practicum.commonData.model.event.dto.EventDto;
import ru.practicum.commonData.model.event.dto.PublicEventsParam;

import java.util.List;

@Service
public class PublicEventsServiceImpl implements PublicEventsService {
    public EventDto getEventById(Long id) {
        return null;
    }

    public List<EventDto> getEvents(PublicEventsParam param) {
        return null;
    }
}
