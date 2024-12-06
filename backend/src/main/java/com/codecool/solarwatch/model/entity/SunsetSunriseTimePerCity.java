package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sunset-sunrise")
@Data
@NoArgsConstructor
public class SunsetSunriseTimePerCity {

  public SunsetSunriseTimePerCity(String sunriseTime, String sunsetTime, City city, LocalDate date) {
    this.sunriseTime = sunriseTime;
    this.sunsetTime = sunsetTime;
    this.city = city;
    this.date = date;
  }

  @Id
  @GeneratedValue
  private long id;
  private String sunriseTime;
  private String sunsetTime;

  @ManyToOne
  @JoinColumn(name = "cityId")
  private City city;
  private LocalDate date;

}
