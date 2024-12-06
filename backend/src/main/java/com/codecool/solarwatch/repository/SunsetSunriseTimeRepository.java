package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.entity.SunsetSunriseTimePerCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunsetSunriseTimeRepository extends JpaRepository<SunsetSunriseTimePerCity, Long> {
  Optional<SunsetSunriseTimePerCity> findSunsetSunriseTimePerCityByCityIdAndDate(Long cityId, LocalDate date);
}
