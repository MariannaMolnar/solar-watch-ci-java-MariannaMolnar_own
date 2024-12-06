package com.codecool.solarwatch.model.payload;

import java.time.LocalDate;

public record NewSunriseSunsetReportDTO(String sunrise, String sunset, String cityName, LocalDate date) {
}
