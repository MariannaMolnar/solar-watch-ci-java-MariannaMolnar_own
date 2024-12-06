package com.codecool.solarwatch.model.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewCityDTO(String name, double lat, double lon, String state, String country) {
}
