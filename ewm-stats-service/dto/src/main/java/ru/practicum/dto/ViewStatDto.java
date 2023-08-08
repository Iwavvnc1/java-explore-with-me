package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Builder(toBuilder = true)
@Data
public class ViewStatDto {

    @NotBlank
    String app;
    @NotBlank
    String uri;
    @NotNull
    Long hits;
}