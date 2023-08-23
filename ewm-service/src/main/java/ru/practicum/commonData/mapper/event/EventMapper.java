package ru.practicum.commonData.mapper.event;

import lombok.experimental.UtilityClass;
import org.hibernate.Hibernate;
import ru.practicum.commonData.mapper.category.CategoryMapper;
import ru.practicum.commonData.model.category.Category;
import ru.practicum.commonData.model.event.Event;
import ru.practicum.commonData.model.event.dto.*;
import ru.practicum.commonData.model.location.Location;
import ru.practicum.commonData.model.user.User;

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
                .participantLimit(eventDto.getParticipantLimit() != null ? eventDto.getParticipantLimit() : 0)
                .description(eventDto.getDescription())
                .title(eventDto.getTitle())
                .location(eventDto.getLocation())
                .paid(eventDto.getPaid() != null ? eventDto.getPaid() : false)
                .requestModeration(eventDto.getRequestModeration() != null ? eventDto.getRequestModeration() : true)
                .build();
    }

    public EventDto toEventDtoFromEvent(Event event) {
        event.setCategory(Hibernate.unproxy(event.getCategory(), Category.class));
        event.setInitiator(Hibernate.unproxy(event.getInitiator(), User.class));
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
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

    public EventDto toEventDtoFromEventViews(Event event) {
        event.setCategory(Hibernate.unproxy(event.getCategory(), Category.class));
        event.setInitiator(Hibernate.unproxy(event.getInitiator(), User.class));
        return EventDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
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
                .views(event.getViews() + 1)
                .build();
    }

    public EventShortDto toEventShortDtoFromEvent(Event event) {
        event.setCategory(Hibernate.unproxy(event.getCategory(), Category.class));
        event.setInitiator(Hibernate.unproxy(event.getInitiator(), User.class));
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

    public Event toEventFromUpdateEventUser(UpdateEventUser dto) {
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

    public Event toEventFromUpdateEventAdmin(UpdateEventAdmin dto) {
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

    public Event updateEventFromUpdateEventAdmin(UpdateEventAdmin dto, Event event) {
        event.setCategory(Hibernate.unproxy(event.getCategory(), Category.class));
        event.setInitiator(Hibernate.unproxy(event.getInitiator(), User.class));
        return Event.builder()
                .annotation(dto.getAnnotation() != null ? dto.getAnnotation() : event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(dto.getDescription() != null ? dto.getDescription() : event.getDescription())
                .eventDate(dto.getEventDate() != null ? dto.getEventDate() : event.getEventDate())
                .id(event.getId())
                .initiator(event.getInitiator())
                .paid(dto.getPaid() != null ? dto.getPaid() : event.getPaid())
                .location(dto.getLocation() != null ? new Location(dto.getLocation().getLat(),
                        dto.getLocation().getLon()) : new Location(event.getLocation().getLat(),
                        event.getLocation().getLon()))
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .views(event.getViews())
                .title(dto.getTitle() != null ? dto.getTitle() : event.getTitle())
                .build();
    }

    public Event updateEventFromUpdateEventUsers(UpdateEventUser dto, Event event) {
        event.setCategory(Hibernate.unproxy(event.getCategory(), Category.class));
        event.setInitiator(Hibernate.unproxy(event.getInitiator(), User.class));
        return Event.builder()
                .annotation(dto.getAnnotation() != null ? dto.getAnnotation() : event.getAnnotation())
                .category(event.getCategory())
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(dto.getDescription() != null ? dto.getDescription() : event.getDescription())
                .eventDate(dto.getEventDate() != null ? dto.getEventDate() : event.getEventDate())
                .id(event.getId())
                .initiator(event.getInitiator())
                .paid(dto.getPaid() != null ? dto.getPaid() : event.getPaid())
                .location(dto.getLocation() != null ? new Location(dto.getLocation().getLat(),
                        dto.getLocation().getLon()) : new Location(event.getLocation().getLat(),
                        event.getLocation().getLon()))
                .participantLimit(dto.getParticipantLimit() != null ? dto.getParticipantLimit() : event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .views(event.getViews())
                .title(dto.getTitle() != null ? dto.getTitle() : event.getTitle())
                .build();
    }

    public List<EventDto> toEventDtoListFromListEvents(List<Event> events) {
        return events.stream().map(EventMapper::toEventDtoFromEvent).collect(Collectors.toList());
    }

    public List<EventShortDto> toEventShortDtoListFromListEvents(List<Event> events) {
        return events.stream().map(EventMapper::toEventShortDtoFromEvent).collect(Collectors.toList());
    }
}
