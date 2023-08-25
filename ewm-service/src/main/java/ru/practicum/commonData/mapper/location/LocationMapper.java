package ru.practicum.commonData.mapper.location;

import lombok.experimental.UtilityClass;
import ru.practicum.commonData.model.location.Location;
import ru.practicum.commonData.model.location.dto.LocationDto;

@UtilityClass
public class LocationMapper {
    public LocationDto toLocationDto(Location location) {
        return LocationDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }

    public Location toLocation(LocationDto dto) {
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }
}
