package ru.practicum.commonData.model.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.commonData.enums.StateAction;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateEventUser extends UpdateEventRequest {
    private StateAction stateAction;
}
