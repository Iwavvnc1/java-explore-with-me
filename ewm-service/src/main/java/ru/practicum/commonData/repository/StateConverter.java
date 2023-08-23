package ru.practicum.commonData.repository;

import ru.practicum.commonData.enums.State;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter implements AttributeConverter<State, String> {

    @Override
    public String convertToDatabaseColumn(State state) {
        return state.toString();
    }

    @Override
    public State convertToEntityAttribute(String state) {
        return State.valueOf(state);
    }
}
