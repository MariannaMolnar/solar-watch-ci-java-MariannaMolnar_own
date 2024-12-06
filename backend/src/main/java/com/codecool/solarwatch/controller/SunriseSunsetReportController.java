package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.NewSunriseSunsetReportDTO;
import com.codecool.solarwatch.model.payload.SunriseSunsetReportDTO;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/sunrise-sunset")
public class SunriseSunsetReportController {
  private final SunriseSunsetService sunriseSunsetService;

  @Autowired
  public SunriseSunsetReportController(SunriseSunsetService sunriseSunsetService) {
    this.sunriseSunsetService = sunriseSunsetService;
  }

  @GetMapping("/current")
  public SunriseSunsetReportDTO sunriseSunsetCurrent(@RequestParam String city, @RequestParam String country) {
    return sunriseSunsetService.getSunriseSunsetReportForCurrentDate(city, country.toUpperCase());
  }

  @GetMapping
  public SunriseSunsetReportDTO getSunriseSunsetByDate(@RequestParam String city, @RequestParam String country,
                                                       @RequestParam String date) {
    return sunriseSunsetService.getSunriseSunsetReportForDate(city, country.toUpperCase(), LocalDate.parse(date));
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> createSunriseSunsetReport(@RequestBody NewSunriseSunsetReportDTO sunriseSunsetReport,
                                                        @RequestBody String countryCode) {
    sunriseSunsetService.createSunriseSunsetReport(sunriseSunsetReport, countryCode);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> updateSunriseSunsetReport(@RequestBody SunriseSunsetReportDTO sunriseSunsetReport) {
    sunriseSunsetService.updateSunriseSunsetReport(sunriseSunsetReport);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteSunriseSunsetReport(@PathVariable long id) {
    sunriseSunsetService.deleteSunriseSunsetReport(id);
    return ResponseEntity.ok(null);
  }
}
