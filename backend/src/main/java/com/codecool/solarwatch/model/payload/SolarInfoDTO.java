package com.codecool.solarwatch.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarInfoDTO(SunriseSunsetTimeDTO results) {
}
