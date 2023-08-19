package ru.practicum.commonData.mapper.event;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.location.Location;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.commonData.mapper.category.CategoryMapper.*;
import static ru.practicum.commonData.mapper.user.UserMapper.*;

@UtilityClass
public class EventMapper {
    public Event toEventFromNewEventDto(NewEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .eventDate(eventDto.getEventDate())
                .participantLimit(eventDto.getParticipantLimit())
                .description(eventDto.getDescription())
                .title(eventDto.getTitle())
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid())
                .requestModeration(eventDto.getRequestModeration())
                .build();
    }

    public EventDto toEventDtoFromEvent(Event event) {
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(toUserDtoFromUser(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventShortDto toEventShortDtoFromEvent(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .id(event.getId())
                .initiator(toUserShortDtoFromUser(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEventFromUpdateEventUser(UpdateEventUser dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .paid(dto.getPaid())
                .location(dto.getLocation() != null ? new Location(dto.getLocation().getLat(),
                        dto.getLocation().getLon()) : null)
                .participantLimit(dto.getParticipantLimit())
                .title(dto.getTitle())
                .build();
    }

    public static Event toEventFromUpdateEventAdmin(UpdateEventAdmin dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .paid(dto.getPaid())
                .location(dto.getLocation() != null ? new Location(dto.getLocation().getLat(),
                        dto.getLocation().getLon()) : null)
                .participantLimit(dto.getParticipantLimit())
                .title(dto.getTitle())
                .build();
    }

    public List<EventDto> toEventDtoListFromListEvents(List<Event> events) {
        return events.stream().map(EventMapper::toEventDtoFromEvent).collect(Collectors.toList());
    }

    public List<EventShortDto> toEventShortDtoListFromListEvents(List<Event> events) {
        return events.stream().map(EventMapper::toEventShortDtoFromEvent).collect(Collectors.toList());
    }
}
