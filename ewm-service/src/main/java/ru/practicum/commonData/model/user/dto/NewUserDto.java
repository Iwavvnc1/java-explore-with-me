package ru.practicum.commonData.model.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto {
    @NotBlank
    private String  email;
    @NotBlank
    private String name;
}
