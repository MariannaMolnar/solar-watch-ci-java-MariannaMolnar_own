package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.CityDTO;
import com.codecool.solarwatch.model.payload.NewCityDTO;
import com.codecool.solarwatch.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/city")
public class CityController {
  private final CityService cityService;

  @Autowired
  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @PostMapping("/create")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> createCity(@RequestBody NewCityDTO city) {
    cityService.createCity(city);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/update")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> updateCity(@RequestBody CityDTO city) {
    cityService.updateCity(city);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCity(@PathVariable long id) {
    cityService.deleteCity(id);
    return ResponseEntity.ok(null);
  }
}
