package com.codecool.solarwatch.model.payload;

import java.time.LocalDate;

public record SolarDataInputDTO(String city, String country, String date) {
}
