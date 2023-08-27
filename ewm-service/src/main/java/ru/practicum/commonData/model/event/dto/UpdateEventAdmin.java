package ru.practicum.commonData.model.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.AdminStateAction;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEventAdmin extends UpdateEventRequest {
    private AdminStateAction stateAction;

}
