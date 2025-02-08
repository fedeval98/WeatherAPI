package com.opytha.weatherAPI.dtos;

import com.opytha.weatherAPI.models.enums.Langs;
import com.opytha.weatherAPI.models.enums.Units;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UnitLangDTO {
    @NotBlank
    @NotNull
    private String lang;
    @NotBlank
    @NotNull
    private String unit;
}
