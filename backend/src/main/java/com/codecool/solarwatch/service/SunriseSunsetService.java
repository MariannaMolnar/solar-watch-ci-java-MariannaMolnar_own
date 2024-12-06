package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.*;
import com.codecool.solarwatch.model.payload.*;
import com.codecool.solarwatch.repository.CityRepository;
import com.codecool.solarwatch.repository.SunsetSunriseTimeRepository;
import com.codecool.solarwatch.service.util.Mapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

import static java.lang.String.format;

@Service
public class SunriseSunsetService {
  public static final String TIMEZONE_ID = "Europe/Budapest";
  //private final RestTemplate restTemplate;
  private static final Logger LOGGER = LoggerFactory.getLogger(SunriseSunsetService.class);
  private final SunsetSunriseTimeRepository timeRepository;
  private final CityService cityService;
  private final Mapper mapper;
  private final CityRepository cityRepository;
  private final WebClient webClient;

  @Autowired
  public SunriseSunsetService(WebClient webClient, SunsetSunriseTimeRepository timeRepository, CityService cityService, Mapper mapper, CityRepository cityRepository) {
    this.webClient = webClient;
    this.timeRepository = timeRepository;
    this.cityService = cityService;
    this.mapper = mapper;
    this.cityRepository = cityRepository;
  }

  public SunriseSunsetReportDTO getSunriseSunsetReportForCurrentDate(String city, String countryCode) {
    return getSunriseSunsetReportForDate(city, countryCode, LocalDate.now());
  }


  public SunriseSunsetReportDTO getSunriseSunsetReportForDate(String cityName, String countryCode, LocalDate date) {
    CityDTO city = cityService.getCity(cityName, countryCode);
    SunriseSunsetReportDTO sunriseSunsetReport = getSunriseSunsetReport(city.lat(), city.lon(), date, city);
    return sunriseSunsetReport;
  }

  private SunriseSunsetReportDTO getSunriseSunsetReport(double lat, double lon, LocalDate date, CityDTO city) {
    Optional<SunsetSunriseTimePerCity> dataFromDB = getSunriseTimePerCityFromDatabase(city, date);

    if (dataFromDB.isPresent()) {
      return mapper.sunsetSunriseTimePerCityToSunriseSunsetReportDTO(dataFromDB.get());
    } else {
      NewSunriseSunsetReportDTO sunriseSunsetReport = getSunriseSunsetReportFromApi(lat, lon, date, city.name());
      City cityInDB = cityRepository.findById(city.id()).orElseThrow(() -> new RuntimeException("Error while fetching city"));
      SunsetSunriseTimePerCity newTimePerCity =
              mapper.newSunriseSunsetReportDTOToSunsetSunriseTimePerCity(sunriseSunsetReport, cityInDB);

      long sunriseSunsetReportId = timeRepository.save(newTimePerCity).getId();

      return mapper.newSunriseSunsetReportDTOToSunriseSunsetReportDTO(sunriseSunsetReport, sunriseSunsetReportId);
    }
  }

  //returning SunsetSunriseTimePerCity
//  private SunsetSunriseTimePerCity getSunriseSunsetReport(double lat, double lon, LocalDate date, City city) {
//    Optional<SunsetSunriseTimePerCity> dataFromDB = getSunriseTimePerCityFromDatabase(city, date);
//
//    if (dataFromDB.isPresent()) {
//      return dataFromDB.get();
//    } else {
//      SunriseSunsetReport sunriseSunsetReport = getSunriseSunsetReportFromApi(lat, lon, date, city.getName());
//
//      SunsetSunriseTimePerCity newTimePerCity = new SunsetSunriseTimePerCity(
//              sunriseSunsetReport.sunrise(),
//              sunriseSunsetReport.sunset(),
//              city,
//              sunriseSunsetReport.date()
//      );
//
//      timeRepository.save(newTimePerCity);
//      return newTimePerCity;
//    }
//  }

  private NewSunriseSunsetReportDTO getSunriseSunsetReportFromApi(double lat, double lon, LocalDate date, String cityName) {
    String sunriseSunsetUrl =
            format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s&tzid=%s&formatted=1",
                    lat, lon, date, TIMEZONE_ID);

    SolarInfoDTO sunriseSunsetResponse = webClient
            .get()
                    .uri(sunriseSunsetUrl)
                            .retrieve()
                                    .bodyToMono(SolarInfoDTO.class)
                                            .block();
    LOGGER.info("Response from SunriseSunset API: {}", sunriseSunsetResponse);

    return new NewSunriseSunsetReportDTO(
            sunriseSunsetResponse.results().sunrise(),
            sunriseSunsetResponse.results().sunset(),
            cityName,
            date
    );
  }


  private Optional<SunsetSunriseTimePerCity> getSunriseTimePerCityFromDatabase(CityDTO city, LocalDate date) {
    return timeRepository.findSunsetSunriseTimePerCityByCityIdAndDate(city.id(), date);
  }

  //TODO check if report already exists in db and throw exception if yes
  public void createSunriseSunsetReport(NewSunriseSunsetReportDTO sunriseSunsetReport, String countryCode) {
    Optional<City> cityInDb = cityRepository.findByNameAndCountry(sunriseSunsetReport.cityName(), countryCode);

    if (cityInDb.isEmpty()) {
      throw new RuntimeException(format("City %s doesn't exist in database", sunriseSunsetReport.cityName()));
    }

    CityDTO cityDTO = mapper.cityToCityDTO(cityInDb.get());
    Optional<SunsetSunriseTimePerCity> dataFromDB =
            getSunriseTimePerCityFromDatabase(cityDTO, sunriseSunsetReport.date());

    if (dataFromDB.isPresent()) {
      throw new IllegalArgumentException(format("Sunrise-sunset for city %s and date %s already exists", cityDTO.name(), sunriseSunsetReport.date()));
    }

    SunsetSunriseTimePerCity newSunsetSunriseTimePerCity = new SunsetSunriseTimePerCity(
            sunriseSunsetReport.sunrise(),
            sunriseSunsetReport.sunset(),
            cityInDb.get(),
            sunriseSunsetReport.date()
    );

    timeRepository.save(newSunsetSunriseTimePerCity);

  }

  @Transactional
  public void updateSunriseSunsetReport(SunriseSunsetReportDTO sunriseSunsetReport) {
    Optional<SunsetSunriseTimePerCity> dataInDb = timeRepository.findById(sunriseSunsetReport.id());

    if (dataInDb.isEmpty()) {
      throw new RuntimeException(format("Sunrise-sunset with id %s don't exist in database",
              sunriseSunsetReport.id()));
    }
    SunsetSunriseTimePerCity sunriseSunsetTimePerCity = dataInDb.get();
    sunriseSunsetTimePerCity.setSunriseTime(sunriseSunsetReport.sunrise());
    sunriseSunsetTimePerCity.setSunsetTime(sunriseSunsetReport.sunset());
    sunriseSunsetTimePerCity.setDate(sunriseSunsetReport.date());
    timeRepository.save(sunriseSunsetTimePerCity);
  }

  public void deleteSunriseSunsetReport(long id) {
    Optional<SunsetSunriseTimePerCity> dataInDb = timeRepository.findById(id);
    if (dataInDb.isEmpty()) {
      throw new RuntimeException(format("Sunrise-sunset with id %s don't exist in database", id));
    }
    timeRepository.delete(dataInDb.get());
  }
}
