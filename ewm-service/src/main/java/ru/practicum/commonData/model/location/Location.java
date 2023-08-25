package ru.practicum.commonData.model.location;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Location {
    private Float lat;
    private Float lon;
}
