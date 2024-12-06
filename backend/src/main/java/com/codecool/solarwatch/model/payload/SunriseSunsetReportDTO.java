package com.codecool.solarwatch.model.payload;

import java.time.LocalDate;

public record SunriseSunsetReportDTO(long id, String sunrise, String sunset, String cityName, LocalDate date) {
}
//public record SunriseSunsetReport(LocalDate date, String city, String sunrise, String sunset) {
//}
