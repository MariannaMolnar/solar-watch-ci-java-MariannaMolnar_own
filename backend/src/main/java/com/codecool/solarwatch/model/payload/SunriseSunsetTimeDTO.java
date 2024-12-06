package com.codecool.solarwatch.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetTimeDTO(String sunrise, String sunset) {
}
