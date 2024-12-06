package com.codecool.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name", "country"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {

  @Id
  @GeneratedValue
  private long id;
  private String name;
  private double latitude;
  private double longitude;
  private String state;
  private String country;

  public City(String name, double latitude, double longitude, String state, String country) {
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
    this.state = state;
    this.country = country;
  }




}
