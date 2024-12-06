package com.codecool.solarwatch.model.payload;

public record CityDTO(long id, String name, double lat, double lon, String state, String country) {
}
