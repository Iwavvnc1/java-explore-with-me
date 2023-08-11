package ru.practicum.dto;

import lombok.Builder;
import lombok.Data;


@Builder(toBuilder = true)
@Data
public class ViewStatDto {
    private String app;
    private String uri;
    private Long hits;
}