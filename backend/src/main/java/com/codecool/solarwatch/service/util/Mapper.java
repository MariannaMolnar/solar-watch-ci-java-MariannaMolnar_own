package com.codecool.solarwatch.service.util;

import com.codecool.solarwatch.model.entity.City;
import com.codecool.solarwatch.model.entity.SunsetSunriseTimePerCity;
import com.codecool.solarwatch.model.payload.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

  public NewCityDTO cityToNewCityDTO(City city) {
    return new NewCityDTO(
            city.getName(),
            city.getLatitude(),
            city.getLongitude(),
            city.getState(),
            city.getCountry()
    );
  }

  public City newCityDTOToCity(NewCityDTO newCityDTO) {
    return new City(
            newCityDTO.name(),
            newCityDTO.lat(),
            newCityDTO.lon(),
            newCityDTO.state(),
            newCityDTO.country()
    );
  }

  public CityDTO cityToCityDTO(City city) {
    return new CityDTO(
            city.getId(),
            city.getName(),
            city.getLatitude(),
            city.getLongitude(),
            city.getState(),
            city.getCountry()
    );
  }

  public City cityDTOToCity(CityDTO cityDTO) {
    return new City(
            cityDTO.id(),
            cityDTO.name(),
            cityDTO.lat(),
            cityDTO.lon(),
            cityDTO.state(),
            cityDTO.country()
    );
  }

  public CityGeocodeDTO cityToCityGeocodeDTO(City city) {
    return new CityGeocodeDTO(
            city.getName(),
            city.getLatitude(),
            city.getLongitude()
    );
  }

  public SunriseSunsetReportDTO sunsetSunriseTimePerCityToSunriseSunsetReportDTO(
          SunsetSunriseTimePerCity sunsetSunriseTimePerCity
  ) {
    return new SunriseSunsetReportDTO(
            sunsetSunriseTimePerCity.getId(),
            sunsetSunriseTimePerCity.getSunriseTime(),
            sunsetSunriseTimePerCity.getSunsetTime(),
            sunsetSunriseTimePerCity.getCity().getName(),
            sunsetSunriseTimePerCity.getDate()
    );
  }

  public SunsetSunriseTimePerCity sunriseSunsetReportDTOToSunsetSunriseTimePerCity(
          SunriseSunsetReportDTO sunriseSunsetReportDTO, City city
  ) {
    return new SunsetSunriseTimePerCity(
            sunriseSunsetReportDTO.sunrise(),
            sunriseSunsetReportDTO.sunset(),
            city,
            sunriseSunsetReportDTO.date()
    );
  }

  public SunriseSunsetReportDTO newSunriseSunsetReportDTOToSunriseSunsetReportDTO(NewSunriseSunsetReportDTO newReport,
                                                                                  long createdId) {
    return new SunriseSunsetReportDTO(
            createdId,
            newReport.sunrise(),
            newReport.sunset(),
            newReport.cityName(),
            newReport.date()
    );
  }

  public SunsetSunriseTimePerCity newSunriseSunsetReportDTOToSunsetSunriseTimePerCity(NewSunriseSunsetReportDTO newReport,
                                                                                      City city) {
    return new SunsetSunriseTimePerCity(
            newReport.sunrise(),
            newReport.sunset(),
            city,
            newReport.date()
    );
  }


}
